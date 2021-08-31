package com.bank.repository.account.internal;

import com.bank.domain.db.AccountEntity;
import com.bank.infrastructure.connection.pool.spi.ConnectionPool;
import com.bank.infrastructure.generator.id.spi.IdGenerator;
import com.bank.infrastructure.generator.sql.spi.SqlGenerator;
import com.bank.validator.internal.AccountEntityValidator;
import com.bank.repository.account.spi.AccountRepository;
import org.apache.log4j.Logger;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public final class AccountRepositoryImpl implements AccountRepository {

    private static final Logger logger = Logger.getLogger(AccountRepositoryImpl.class);

    private final AccountEntityValidator validator;
    private final SqlGenerator<AccountEntity> accountEntitySqlGenerator;
    private final ConnectionPool connectionPool;
    private final IdGenerator<AccountEntity, Integer> idGenerator;
    private final String tableName;
    private final Map<String, Field> columns;

    public AccountRepositoryImpl(AccountEntityValidator validator,
                                 SqlGenerator<AccountEntity> accountEntitySqlGenerator,
                                 ConnectionPool connectionPool,
                                 IdGenerator<AccountEntity, Integer> idGenerator,
                                 String tableName, Map<String, Field> columns) {
        this.validator = validator;
        this.accountEntitySqlGenerator = accountEntitySqlGenerator;
        this.connectionPool = connectionPool;
        this.idGenerator = idGenerator;
        this.tableName = tableName;
        this.columns = columns;
    }

    @Override
    public void create(AccountEntity accountEntityDetails) {
        if(!validator.isValid(accountEntityDetails)){
            return;
        }
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String sql = "INSERT INTO " + tableName + "(id, owner_id, cards_table_id, money_id, status) VALUES (?,?,?,?,?);";
            PreparedStatement preparedStatement = this.getPreparedStatement(connection, sql, accountEntityDetails.getId(),
                    accountEntityDetails.getOwnerId(),accountEntityDetails.getCardsTableId(),
                    accountEntityDetails.getMoneyId(), accountEntityDetails.getStatus());

            logger.trace("starting to execute prepared statement: " + sql + "...");
            if(!preparedStatement.execute()){
                logger.trace("execution failed!");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(connection != null){
                logger.trace("releasing connection...");
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public Optional<AccountEntity> readById(Integer id) {
        if(id == null){
            return Optional.empty();
        }
        AccountEntity accountEntity = null;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String sql = "SELECT * FROM " + tableName + " WHERE " + tableName + ".id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id.toString());

            logger.trace("starting to execute prepared statement: " + sql +"...");
            ResultSet resultSet = preparedStatement.getResultSet();

            logger.trace("starting assembling account entity...");
            accountEntity = this.createEntityFromResultSet(resultSet);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(connection != null){
                logger.trace("releasing connection...");
                connectionPool.releaseConnection(connection);
            }
        }
        return Optional.ofNullable(accountEntity);
    }

    @Override
    public Optional<AccountEntity> update(AccountEntity source, Integer id) {
        if(id == null){
            return Optional.empty();
        }
        Connection connection = null;
        AccountEntity accountEntity = null;
        try{
            connection = connectionPool.getConnection();
            String sql = "UPDATE " + tableName +
                    " SET owner_id = ?, cards_table_id = ?, money_id = ?, status = ?" +
                    " WHERE "+tableName+".id =:?;";

            PreparedStatement preparedStatement = this.getPreparedStatement(connection, sql,
                    source.getOwnerId(), source.getCardsTableId(), source.getMoneyId(), source.getStatus(), id);

            logger.trace("starting to execute prepared statement: " + sql +"...");
            ResultSet resultSet = preparedStatement.getResultSet();

            logger.trace("starting assembling updated account entity...");
            accountEntity = this.createEntityFromResultSet(resultSet);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(connection != null){
                logger.trace("releasing connection...");
                connectionPool.releaseConnection(connection);
            }
        }
        return Optional.ofNullable(accountEntity);
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            return;
        }
        Connection connection = null;
        try{
            connection = connectionPool.getConnection();
            String sql = "DELETE " + tableName + " WHERE " + tableName + ".id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id.toString());

            logger.trace("starting to execute prepared statement: " + sql + "...");
            if(!preparedStatement.execute()){
                logger.trace("execution failed!");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(connection != null){
                logger.trace("releasing connection...");
                connectionPool.releaseConnection(connection);
            }
        }
    }

    @Override
    public Optional<AccountEntity> findAnyMatching(AccountEntity accountDetails) {
        Connection connection = null;
        AccountEntity accountEntity = null;
        try {
            connection = connectionPool.getConnection();
            String sql = accountEntitySqlGenerator.generateSqlQueryFromTemplate(tableName, columns, accountDetails);

            PreparedStatement preparedStatement = this.getPreparedStatement(connection, sql, accountDetails.getId(),
                    accountDetails.getOwnerId(),accountDetails.getCardsTableId(),
                    accountDetails.getMoneyId(), accountDetails.getStatus());

            logger.trace("starting to execute prepared statement: " + sql +"...");
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.setFetchSize(1);

            logger.trace("starting assembling account entity...");
            accountEntity = this.createEntityFromResultSet(resultSet);
        }
        catch (SQLException e ) {
            e.printStackTrace();
        }
        finally {
            if(connection != null){
                logger.trace("releasing connection...");
                connectionPool.releaseConnection(connection);
            }
        }
        return Optional.ofNullable(accountEntity);
    }

    @Override
    public List<AccountEntity> findAllMatching(AccountEntity accountDetails) {
        Connection connection = null;
        List<AccountEntity> accountEntities = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            String sql = accountEntitySqlGenerator.generateSqlQueryFromTemplate(tableName, columns, accountDetails);

            PreparedStatement preparedStatement = this.getPreparedStatement(connection, sql, accountDetails.getId(),
                    accountDetails.getOwnerId(),accountDetails.getCardsTableId(),
                    accountDetails.getMoneyId(), accountDetails.getStatus());

            logger.trace("starting to execute prepared statement: " + sql +"...");
            ResultSet resultSet = preparedStatement.getResultSet();


            logger.trace("starting assembling account entity list...");
            while(resultSet.next()){
                accountEntities.add(this.createEntityFromResultSet(resultSet));
            }

        }
        catch (SQLException e ) {
            e.printStackTrace();
        }
        finally {
            if(connection != null){
                logger.trace("releasing connection...");
                connectionPool.releaseConnection(connection);
            }
        }
        return accountEntities;
    }

    private PreparedStatement getPreparedStatement(Connection connection, String sql, Object... objects) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int index = 1;
        for(Object object : objects){
            preparedStatement.setObject(index, object.toString());
            index += 1;
        }
        return preparedStatement;
    }

    private AccountEntity createEntityFromResultSet(ResultSet resultSet) throws SQLException {
        AccountEntity accountEntity = new AccountEntity();
        try {
            for(String column : columns.keySet()){
                Field field = columns.get(column);
                field.setAccessible(true);
                Object casted = field.getType().cast(resultSet.getObject(column));
                field.set(accountEntity, casted);
            }
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return accountEntity;
    }
}
