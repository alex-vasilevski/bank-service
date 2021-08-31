package com.bank.repository.card.internal;

import com.bank.domain.db.CardEntity;
import com.bank.infrastructure.connection.pool.spi.ConnectionPool;
import com.bank.infrastructure.factory.entity.spi.EntityFactory;
import com.bank.infrastructure.factory.statement.spi.PreparedStatementFactory;
import com.bank.infrastructure.factory.generator.sql.spi.SqlGenerator;
import com.bank.repository.account.internal.AccountRepositoryImpl;
import com.bank.repository.card.spi.CardRepository;
import com.bank.validator.internal.CardEntityValidator;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CardRepositoryImpl implements CardRepository {

    private static final Logger logger = Logger.getLogger(AccountRepositoryImpl.class);

    private final EntityFactory entityFactory;
    private final PreparedStatementFactory preparedStatementFactory;
    private final CardEntityValidator validator;
    private final SqlGenerator<CardEntity> selectCardEntityByParamsSqlGenerator;
    private final ConnectionPool connectionPool;
    private final String tableName;
    private final Map<String, Field> fieldMapping;

    public CardRepositoryImpl(EntityFactory entityFactory, PreparedStatementFactory preparedStatementFactory,
                              CardEntityValidator validator, SqlGenerator<CardEntity> selectCardEntityByParamsSqlGenerator,
                              ConnectionPool connectionPool, String tableName, Map<String, Field> fieldMapping) {
        this.entityFactory = entityFactory;
        this.preparedStatementFactory = preparedStatementFactory;
        this.validator = validator;
        this.selectCardEntityByParamsSqlGenerator = selectCardEntityByParamsSqlGenerator;
        this.connectionPool = connectionPool;
        this.tableName = tableName;
        this.fieldMapping = fieldMapping;
    }

    @Override
    public void create(CardEntity cardDetails) {
        logger.trace("validating data...");
        if(!validator.isValid(cardDetails)){
            logger.trace("execution failed, data is not valid.");
            return;
        }

        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String sql = "INSERT INTO " + tableName + String.format("(%s, %s, %s, ) VALUES (?,?,?);", fieldMapping.keySet().toArray());
            PreparedStatement preparedStatement = preparedStatementFactory.getPreparedStatement(connection, sql, cardDetails.getId(),
                    cardDetails.getOwnerId(), cardDetails.getAccountId());

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
    public Optional<CardEntity> readById(Integer id) {
        logger.trace("validating data...");
        if(id == null){
            logger.trace("execution failed, data is not valid.");
            return Optional.empty();
        }
        CardEntity cardEntity = null;
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
            cardEntity = (CardEntity) entityFactory.createEntityFromResultSet(resultSet, fieldMapping, CardEntity.class);
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
        return Optional.ofNullable(cardEntity);
    }

    @Override
    public void update(CardEntity source, Integer id)  {
        logger.trace("validating data...");
        if(id == null){
            logger.trace("execution failed, data is not valid.");
            return;
        }

        Connection connection = null;
        try{
            connection = connectionPool.getConnection();
            String sql = "UPDATE " + tableName +
                    " SET owner_id = ?, account_id = ?" +
                    " WHERE "+tableName+".id =:?;";

            PreparedStatement preparedStatement = preparedStatementFactory.getPreparedStatement(connection, sql,
                    source.getOwnerId(), source.getAccountId(),  id);

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
    public void delete(Integer id)  {
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
    public Optional<CardEntity> findAnyMatching(CardEntity cardDetails) {
        Connection connection = null;
        CardEntity cardEntity = null;
        try {
            connection = connectionPool.getConnection();
            String sql = selectCardEntityByParamsSqlGenerator.generateSqlQueryFromTemplate(tableName, fieldMapping, cardDetails);

            PreparedStatement preparedStatement = preparedStatementFactory.getPreparedStatement(connection, sql, cardDetails.getId(),
                    cardDetails.getOwnerId(), cardDetails.getAccountId());

            logger.trace("transaction begin");
            ResultSet resultSet = preparedStatement.getResultSet();
            logger.trace("transaction end successfully.");
            resultSet.setFetchSize(1);

            logger.trace("starting assembling entity...");
            cardEntity = (CardEntity) entityFactory.createEntityFromResultSet(resultSet, fieldMapping, CardEntity.class);
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
        return Optional.ofNullable(cardEntity);
    }

    @Override
    public List<CardEntity> findAllMatching(CardEntity cardDetails) {
        Connection connection = null;
        List<CardEntity> accountEntities = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            String sql = selectCardEntityByParamsSqlGenerator.generateSqlQueryFromTemplate(tableName, fieldMapping, cardDetails);

            PreparedStatement preparedStatement = preparedStatementFactory.getPreparedStatement(connection, sql, cardDetails.getId(),
                    cardDetails.getOwnerId(),cardDetails.getAccountId());

            logger.trace("transaction begin");
            ResultSet resultSet = preparedStatement.getResultSet();
            logger.trace("transaction end successfully.");


            logger.trace("starting assembling entity list...");
            while(resultSet.next()){
                accountEntities.add((CardEntity) entityFactory.createEntityFromResultSet(resultSet, fieldMapping, CardEntity.class));
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
