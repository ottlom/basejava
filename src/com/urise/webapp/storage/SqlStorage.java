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
        helper.executeSqlTransaction((query) -> {
            try (PreparedStatement ps = query.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            addContacts(r, query);
            return addSections(r, query);
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
        helper.executeSqlTransaction((query) -> {
            try (PreparedStatement ps = query.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }

            try (PreparedStatement ps = query.prepareStatement("" +
                    "   DELETE FROM contact c WHERE resume_uuid = ?;" +
                    "   DELETE FROM section s WHERE resume_uuid = ?")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getUuid());
                ps.execute();
            }

            addContacts(r, query);
            return addSections(r, query);
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
            if (!result.next()) {
                throw new NotExistStorageException("database still empty");
            }
            return result.getInt(1);
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.executeSqlQuery(" " +
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
        ArrayList<Resume> sortedList = new ArrayList<>();
        helper.executeSqlQuery(" " +
                "   SELECT * FROM resume " +
                "   ORDER BY uuid ", ((ps) -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sortedList.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return null;
        }));

        helper.executeSqlQuery(" " +
                "   SELECT * FROM contact c " +
                "   LEFT JOIN section s" +
                "   ON c.resume_uuid = s.resume_uuid " +
                "   ORDER BY c.resume_uuid ", (preparedStatement) -> {
            ResultSet res = preparedStatement.executeQuery();
            Resume currentResume = null;
            while (res.next()) {
                String resumeUuid = res.getString("resume_uuid");
                if (currentResume == null || !currentResume.getUuid().equals(resumeUuid)) {
                    currentResume = sortedList.stream()
                            .filter(r -> r.getUuid().equals(resumeUuid))
                            .findFirst()
                            .orElse(null);
                }
                if (currentResume != null) {
                    addContact(res, currentResume);
                    addSection(res, currentResume);
                }
            }
            return null;
        });
        return sortedList;
    }

    private void addContacts(Resume r, Connection query) throws SQLException {
        try (PreparedStatement ps =
                     query.prepareStatement("INSERT INTO contact (typecontact, contactvalue, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, e.getKey().name());
                ps.setString(2, e.getValue());
                ps.setString(3, r.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private Resume addSections(Resume r, Connection query) throws SQLException {
        try (PreparedStatement ps =
                     query.prepareStatement("INSERT INTO section (typesection, sectionvalue, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                ps.setString(1, e.getKey().name());
                ps.setString(2, e.getValue().toString());
                ps.setString(3, r.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
            return null;
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
            if (sectionType.equals("PERSONAL") || sectionType.equals("OBJECTIVE")) {
                r.addSection(SectionType.valueOf(sectionType), new TextSection(rs.getString("sectionvalue")));
            } else if (sectionType.equals("ACHIEVEMENT") || sectionType.equals("QUALIFICATIONS")) {
                String[] items = sectionValue.split("\n");
                ArrayList<String> dataListSection = new ArrayList<>(Arrays.asList(items));
                r.addSection(SectionType.valueOf(sectionType), new ListSection(dataListSection));
            }
        }
    }
}

