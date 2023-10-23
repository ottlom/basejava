package com.urise.webapp.storage;

import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.storage.exception.NotExistStorageException;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        helper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void save(Resume r) {
        helper.executeSqlTransaction((conn) -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContact(r, conn);
            insertSection(r, conn);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        helper.executeSqlQuery("DELETE FROM resume WHERE uuid=?", (preparedStatement) -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        helper.executeSqlTransaction((conn) -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }

            deleteContacts(r, conn, "DELETE FROM contact c WHERE resume_uuid = ?");
            deleteSections(r, conn, "DELETE FROM section s WHERE resume_uuid = ?");

            insertContact(r, conn);
            insertSection(r, conn);
            return null;
        });
    }

    @Override
    public void clear() {
        helper.execute("DELETE FROM resume");
    }

    @Override
    public int size() {
        return helper.executeSqlQuery("SELECT count(*) FROM resume", (preparedStatement) -> {
            ResultSet result = preparedStatement.executeQuery();
            return result.next() ? result.getInt(1) : 0;
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.executeSqlQuery(
                "SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "ON r.uuid = c.resume_uuid " +
                        "LEFT JOIN section s " +
                        "ON r.uuid = s.resume_uuid " +
                        "WHERE r.uuid=? ",
                (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(rs, resume);
                        addSection(rs, resume);
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> sortedMap = new LinkedHashMap<>();

        helper.executeSqlQuery(
                "SELECT * FROM resume " +
                        "ORDER BY full_name ", ((ps) -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String uuidKey = rs.getString("uuid");
                        sortedMap.put(uuidKey, new Resume(uuidKey, rs.getString("full_name")));
                    }
                    return null;
                }));

        helper.executeSqlQuery(
                "SELECT * FROM contact c " +
                        "RIGHT JOIN resume r " +
                        "ON c.resume_uuid = r.uuid ", (preparedStatement) -> {
                    ResultSet res = preparedStatement.executeQuery();
                    while (res.next()) {
                        Resume currentResume = sortedMap.get(res.getString("resume_uuid"));
                        addContact(res, currentResume);
                    }
                    return null;
                });

        helper.executeSqlQuery(
                "SELECT * FROM section s " +
                        "RIGHT JOIN resume r " +
                        "ON s.resume_uuid = r.uuid ", (preparedStatement) -> {
                    ResultSet res = preparedStatement.executeQuery();
                    while (res.next()) {
                        Resume currentResume = sortedMap.get(res.getString("resume_uuid"));
                        addSection(res, currentResume);
                    }
                    return null;
                });

        return new ArrayList<>(sortedMap.values());
    }

    private static void deleteContacts(Resume r, Connection conn, String table) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(table)) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private static void deleteSections(Resume r, Connection conn, String table) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(table)) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void insertContact(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps =
                     conn.prepareStatement("INSERT INTO contact (typecontact, contactvalue, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, e.getKey().name());
                ps.setString(2, e.getValue());
                ps.setString(3, r.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private String convertAbstractSectionToString(AbstractSection abstractSection) {
        String returnString = null;
        if (abstractSection instanceof TextSection) {
            returnString = ((TextSection) abstractSection).getText();
        } else if (abstractSection instanceof ListSection) {
            List<String> list = ((ListSection) abstractSection).getList();
            returnString = String.join("\n", list);
        }
        return returnString;
    }

    private void insertSection(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps =
                     conn.prepareStatement("INSERT INTO section (typesection, sectionvalue, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                ps.setString(1, e.getKey().name());
                ps.setString(2, convertAbstractSectionToString(e.getValue()));
                ps.setString(3, r.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("contactvalue");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("typecontact")), value);
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String sectionType = rs.getString("typesection");
        String sectionValue = rs.getString("sectionvalue");

        if (sectionType != null && sectionValue != null) {
            switch (sectionType) {
                case "PERSONAL":
                case "OBJECTIVE":
                    r.addSection(SectionType.valueOf(sectionType), new TextSection(rs.getString("sectionvalue")));
                    break;
                case "ACHIEVEMENT":
                case "QUALIFICATIONS":
                    r.addSection(SectionType.valueOf(sectionType), new ListSection(new ArrayList<>(Arrays.asList(sectionValue.split("\n")))));
                    break;
            }
        }
    }
}

