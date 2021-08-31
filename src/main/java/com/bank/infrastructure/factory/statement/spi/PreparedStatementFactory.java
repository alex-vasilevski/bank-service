package com.bank.infrastructure.factory.statement.spi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementFactory {
    public PreparedStatement getPreparedStatement(Connection connection, String sqlTemplate, Object... objects) throws SQLException;
}
