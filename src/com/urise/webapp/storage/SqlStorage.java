package com.urise.webapp.storage;

import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.storage.exception.NotExistStorageException;
import com.urise.webapp.util.JsonParser;

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
            insertContacts(r, conn);
            insertSections(r, conn);
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

            insertContacts(r, conn);
            insertSections(r, conn);
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
        return helper.executeSqlTransaction(connection -> {
            Map<String, Resume> sortedMap = new LinkedHashMap<>();
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM resume " +
                            "ORDER BY full_name, uuid ")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuidKey = rs.getString("uuid");
                    sortedMap.put(uuidKey, new Resume(uuidKey, rs.getString("full_name")));
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact c ")) {
                ResultSet res = ps.executeQuery();
                while (res.next()) {
                    Resume currentResume = sortedMap.get(res.getString("resume_uuid"));
                    addContact(res, currentResume);
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM section s ")) {
                ResultSet res = ps.executeQuery();
                while (res.next()) {
                    Resume currentResume = sortedMap.get(res.getString("resume_uuid"));
                    addSection(res, currentResume);
                }
            }
            return new ArrayList<>(sortedMap.values());
        });
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

    private void insertContacts(Resume r, Connection conn) throws SQLException {
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

    private void insertSections(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps =
                     conn.prepareStatement("INSERT INTO section (typesection, sectionvalue, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                ps.setString(1, e.getKey().name());
                ps.setString(2, JsonParser.write(e.getValue(), AbstractSection.class));
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
        if (sectionValue != null) {
            r.addSection(SectionType.valueOf(sectionType), JsonParser.read(sectionValue, AbstractSection.class));
        }
    }
}

