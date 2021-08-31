package com.bank.infrastructure.factory.generator.sql.internal;

import com.bank.infrastructure.factory.generator.sql.spi.SqlGenerator;

import java.lang.reflect.Field;
import java.util.Map;

public class SelectByParamsSqlGenerator<Entity> implements SqlGenerator<Entity> {

    @Override
    public String generateSqlQueryFromTemplate(String tableName, Map<String, Field> columnsMapping, Entity target) {
        String template = "SELECT * FROM " + tableName + " WHERE ";
        StringBuilder builder = new StringBuilder(template);
        try {
            for(String column : columnsMapping.keySet()){
                Field field = columnsMapping.get(column);
                field.setAccessible(true);
                if(field.get(target) != null){
                    builder.append(tableName + "." + column + " = ?,");
                }
            }
            builder.deleteCharAt(builder.length() - 1).append(";");

        }
        catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return builder.toString();
    }
}
