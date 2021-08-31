package com.bank.infrastructure.factory.statement.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementFactoryImpl {
    public PreparedStatement getPreparedStatement(Connection connection, String sqlTemplate, Object... objects) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sqlTemplate);
        int index = 1;
        for(Object object : objects){
            preparedStatement.setObject(index, object.toString());
            index += 1;
        }
        return preparedStatement;
    }

}
