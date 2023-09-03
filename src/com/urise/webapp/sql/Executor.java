package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Executor<T> {
    T doSqlQuery(PreparedStatement ps) throws SQLException;
}
