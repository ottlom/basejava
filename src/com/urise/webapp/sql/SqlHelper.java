package com.urise.webapp.sql;

import com.urise.webapp.storage.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T executeSqlQuery(String query, Executor<T> executor) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            return executor.doSqlQuery(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public void execute(String query) {
        executeSqlQuery(query, PreparedStatement::execute);
    }
}
