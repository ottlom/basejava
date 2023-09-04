package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.storage.exception.NotExistStorageException;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper helper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        helper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void save(Resume r) {
        helper.executeSqlQuery("INSERT INTO resume (uuid, full_name) VALUES (?,?)", (preparedStatement) -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.setString(2, r.getFullName());
            preparedStatement.execute();
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
        helper.executeSqlQuery("UPDATE resume SET full_name=? WHERE uuid=?", (preparedStatement) -> {
            preparedStatement.setString(1, r.getFullName());
            preparedStatement.setString(2, r.getUuid());

            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }

            preparedStatement.execute();
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
            if (!result.next()) {
                throw new NotExistStorageException("database still empty");
            }
            return result.getInt(1);
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.executeSqlQuery("SELECT * FROM resume r WHERE r.uuid = ?", (ps) -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return helper.executeSqlQuery("SELECT * FROM resume ORDER BY full_name,uuid", (preparedStatement) -> {
            List<Resume> listResume = new ArrayList<>();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                listResume.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return listResume;
        });
    }
}
