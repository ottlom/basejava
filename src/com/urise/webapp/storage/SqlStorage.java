package com.urise.webapp.storage;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.storage.exception.NotExistStorageException;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
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
            return addContacts(r, query);
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
                ps.execute();
            }

            try (PreparedStatement ps = query.prepareStatement("DELETE FROM contact c WHERE resume_uuid =?")) {
                ps.setString(1, r.getUuid());
                ps.execute();
            }
            return addContacts(r, query);
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
                        "WHERE r.uuid=? ",
                (ps) -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        String value = rs.getString("values");
                        resume.addContact(type, value);
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.executeSqlQuery("" +
                "   SELECT * FROM resume r " +
                "LEFT JOIN contact c " +
                "ON r.uuid = c.resume_uuid " +
                "ORDER BY full_name,uuid ", (preparedStatement) -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Resume resume = map.get(uuid);

                if (resume == null) {
                    resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                    map.put(uuid, resume);
                }
                attachContacts(rs, resume);
            }
            return new ArrayList<>(map.values());
        });
    }

    private Resume addContacts(Resume r, Connection query) throws SQLException {
        try (PreparedStatement ps =
                     query.prepareStatement("INSERT INTO contact (type, values, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, e.getKey().name());
                ps.setString(2, e.getValue());
                ps.setString(3, r.getUuid());
                ps.addBatch();
            }
            ps.executeBatch();
            return null;
        }
    }

    private void attachContacts(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("values");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }
}

