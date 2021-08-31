package com.bank.infrastructure.factory.entity.spi;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Map;

public interface EntityFactory {
    Object createEntityFromResultSet(ResultSet resultSet, Map<String, Field> mapping, Class<?> entityClass);
}
