package com.bank.repository.account.internal;

import com.bank.domain.db.AccountEntity;
import com.bank.infrastructure.connection.pool.spi.ConnectionPool;
import com.bank.infrastructure.factory.entity.spi.EntityFactory;
import com.bank.infrastructure.factory.statement.spi.PreparedStatementFactory;
import com.bank.infrastructure.factory.generator.id.spi.IdGenerator;
import com.bank.infrastructure.factory.generator.sql.spi.SqlGenerator;
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
    private final PreparedStatementFactory preparedStatementFactory;
    private final EntityFactory entityFactory;
    private final AccountEntityValidator validator;
    private final SqlGenerator<AccountEntity> selectAccountEntityByParamsSqlGenerator;
    private final ConnectionPool connectionPool;
    private final IdGenerator<AccountEntity, Integer> idGenerator;
    private final String tableName;
    private final Map<String, Field> fieldMapping;


    public AccountRepositoryImpl(PreparedStatementFactory preparedStatementFactory, EntityFactory entityFactory,
                                 AccountEntityValidator validator, SqlGenerator<AccountEntity> selectAccountEntityByParamsSqlGenerator,
                                 ConnectionPool connectionPool, IdGenerator<AccountEntity, Integer> idGenerator,
                                 String tableName, Map<String, Field> fieldMapping) {
        this.preparedStatementFactory = preparedStatementFactory;
        this.entityFactory = entityFactory;
        this.validator = validator;
        this.selectAccountEntityByParamsSqlGenerator = selectAccountEntityByParamsSqlGenerator;
        this.connectionPool = connectionPool;
        this.idGenerator = idGenerator;
        this.tableName = tableName;
        this.fieldMapping = fieldMapping;
    }

    @Override
    public void create(AccountEntity accountEntityDetails) {
        logger.trace("validating data...");
        if(!validator.isValid(accountEntityDetails)){
            logger.trace("execution failed, data is not valid.");
            return;
        }

        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String sql = "INSERT INTO " + tableName + "(id, owner_id, cards_table_id, money_id, status) VALUES (?,?,?,?,?);";
            PreparedStatement preparedStatement = preparedStatementFactory.getPreparedStatement(connection, sql, accountEntityDetails.getId(),
                    accountEntityDetails.getOwnerId(),accountEntityDetails.getCardsTableId(),
                    accountEntityDetails.getMoneyId(), accountEntityDetails.getStatus());

            logger.trace("transaction begin");
            if(!preparedStatement.execute()){
                connection.rollback();
                logger.trace("execution failed!\ntransaction rolled back.");
            }
            else {
                connection.commit();
                logger.trace("transaction end successfully.");
            }
        }
        catch (SQLException e) {
            logger.trace("execution failed!\ntransaction cannot be rolled back.");
            e.printStackTrace();
        }
        finally {
            if(connection != null){
                connectionPool.releaseConnection(connection);
            }
        }
    }
    @Override
    public Optional<AccountEntity> readById(Integer id) {
        logger.trace("validating data...");
        if(id == null){
            logger.trace("execution failed, data is not valid.");
            return Optional.empty();
        }
        AccountEntity accountEntity = null;
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String sql = "SELECT * FROM " + tableName + " WHERE " + tableName + ".id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id.toString());

            logger.trace("transaction begin");
            ResultSet resultSet = preparedStatement.getResultSet();
            logger.trace("transaction end successfully.");

            logger.trace("starting assembling entity...");
            accountEntity = (AccountEntity) entityFactory.createEntityFromResultSet(resultSet, fieldMapping, AccountEntity.class);
        }
        catch (SQLException e) {
            logger.trace("execution failed!\ntransaction cannot be rolled back.");
            e.printStackTrace();
        }
        finally {
            if(connection != null){

                connectionPool.releaseConnection(connection);
            }
        }
        return Optional.ofNullable(accountEntity);
    }
    @Override
    public void update(AccountEntity source, Integer id) {
        logger.trace("validating data...");
        if(id == null){
            logger.trace("execution failed, data is not valid.");
            return;
        }

        Connection connection = null;
        AccountEntity accountEntity = null;
        try{
            connection = connectionPool.getConnection();
            String sql = "UPDATE " + tableName +
                    " SET owner_id = ?, cards_table_id = ?, money_id = ?, status = ?" +
                    " WHERE "+tableName+".id =:?;";

            PreparedStatement preparedStatement = preparedStatementFactory.getPreparedStatement(connection, sql,
                    source.getOwnerId(), source.getCardsTableId(), source.getMoneyId(), source.getStatus(), id);

            logger.trace("transaction begin");
            preparedStatement.executeUpdate();
            connection.commit();
            logger.trace("transaction end successfully.");
        }
        catch (SQLException e) {
            logger.trace("execution failed!\ntransaction cannot be rolled back.");
            e.printStackTrace();
        }
        finally {
            if(connection != null){
                connectionPool.releaseConnection(connection);
            }
        }
    }
    @Override
    public void delete(Integer id) {
        logger.trace("validating data...");
        if(id == null){
            logger.trace("execution failed, data is not valid.");
            return;
        }
        Connection connection = null;
        try{
            connection = connectionPool.getConnection();
            String sql = "DELETE " + tableName + " WHERE " + tableName + ".id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, id.toString());

            logger.trace("transaction begin");
            if(!preparedStatement.execute()){
                connection.rollback();
                logger.trace("execution failed!\ntransaction rolled back.");
            }
            else{
                connection.commit();
                logger.trace("transaction end successfully.");
            }
        }
        catch (SQLException e) {
            logger.trace("execution failed!\ntransaction cannot be rolled back.");
            e.printStackTrace();
        }
        finally {
            if(connection != null){
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
            String sql = selectAccountEntityByParamsSqlGenerator.generateSqlQueryFromTemplate(tableName, fieldMapping, accountDetails);

            PreparedStatement preparedStatement = preparedStatementFactory.getPreparedStatement(connection, sql, accountDetails.getId(),
                    accountDetails.getOwnerId(),accountDetails.getCardsTableId(),
                    accountDetails.getMoneyId(), accountDetails.getStatus());

            logger.trace("transaction begin");
            ResultSet resultSet = preparedStatement.getResultSet();
            logger.trace("transaction end successfully.");
            resultSet.setFetchSize(1);

            logger.trace("starting assembling entity...");
            accountEntity = (AccountEntity) entityFactory.createEntityFromResultSet(resultSet, fieldMapping, AccountEntity.class);
        }
        catch (SQLException e ) {
            logger.trace("execution failed!\ntransaction cannot be rolled back.");
            e.printStackTrace();
        }
        finally {
            if(connection != null){
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
            String sql = selectAccountEntityByParamsSqlGenerator.generateSqlQueryFromTemplate(tableName, fieldMapping, accountDetails);

            PreparedStatement preparedStatement = preparedStatementFactory.getPreparedStatement(connection, sql, accountDetails.getId(),
                    accountDetails.getOwnerId(),accountDetails.getCardsTableId(),
                    accountDetails.getMoneyId(), accountDetails.getStatus());

            logger.trace("transaction begin");
            ResultSet resultSet = preparedStatement.getResultSet();
            logger.trace("transaction end successfully.");


            logger.trace("starting assembling entity list...");
            while(resultSet.next()){
                accountEntities.add((AccountEntity) entityFactory.createEntityFromResultSet(resultSet, fieldMapping, AccountEntity.class));
            }

        }
        catch (SQLException e ) {
            logger.trace("execution failed!\ntransaction cannot be rolled back.");
            e.printStackTrace();
        }
        finally {
            if(connection != null){
                connectionPool.releaseConnection(connection);
            }
        }
        return accountEntities;
    }

}
