package com.bank.infrastructure.factory.entity.internal;

import com.bank.infrastructure.factory.entity.spi.EntityFactory;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Map;

public class EntityFactoryImpl implements EntityFactory {

    @Override
    public Object createEntityFromResultSet(ResultSet resultSet, Map<String, Field> tableMapping, Class<?> entityClass) {
        Object object = null;
        try {
            object = entityClass.getConstructor().newInstance();
            for(String column : tableMapping.keySet()){
                Field field = tableMapping.get(column);
                field.setAccessible(true);
                Object casted = field.getType().cast(resultSet.getObject(column));
                field.set(object, casted);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
