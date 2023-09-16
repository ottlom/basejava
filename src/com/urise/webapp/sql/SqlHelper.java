package com.urise.webapp.sql;

import com.urise.webapp.storage.exception.ExistStorageException;
import com.urise.webapp.storage.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String query) {
        executeSqlQuery(query, PreparedStatement::execute);
    }

    public <T> T executeSqlQuery(String query, Executor<T> executor) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            return executor.doSqlQuery(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(null);
            } else {
                throw new StorageException(e);
            }
        }
    }

    public <T> T executeSqlTransaction(SqlTransaction<T> transaction) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T res = transaction.execute(connection);
                connection.commit();
                return res;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(null);
            } else {
                throw new StorageException(e);
            }
        }
    }
}
