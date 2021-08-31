package com.bank.infrastructure.connection.pool.internal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasicConnectionPoolTest {

    private final String URL = "jdbc:h2:mem:testdb";
    private final String USER = "sa";
    private final String PASSWORD = "1111";

    private BasicConnectionPool connectionPool;

    @AfterEach
    void setConnectionPoolToNull(){
        connectionPool = null;
    }

    @Test
    void shouldCreateConnectionAndPutItInPool() {
        connectionPool = BasicConnectionPool.create(URL, USER, PASSWORD);
        assertNotNull(connectionPool);
    }

    @Test
    void shouldReturnNullWhenCannotCreateAndStoreConnectionsWithGivenParameters(){
        connectionPool = BasicConnectionPool.create("", "", "");
        assertNull(connectionPool);
    }

    @Test
    void shouldGetValidConnection() throws SQLException {
        connectionPool = BasicConnectionPool.create(URL, USER, PASSWORD);
        Connection actual = connectionPool.getConnection();
        assertNotNull(actual);
    }

    @Test
    void shouldThrowExceptionWhenConnectionPoolMaximumReached() throws SQLException {
        connectionPool = BasicConnectionPool.create(URL, USER, PASSWORD);
        int poolMaximum = BasicConnectionPool.getMaxPoolSize();
        List<Connection> connections = new ArrayList<>();
        for(int index = 0; index < poolMaximum; ++index){
            connections.add(connectionPool.getConnection());
        }

        assertThrows(SQLException.class, () -> connectionPool.getConnection());
    }
}