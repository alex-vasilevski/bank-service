package com.bank.infrastructure.factory.generator.sql.spi;

import java.lang.reflect.Field;
import java.util.Map;

public interface SqlGenerator<Entity> {
    String generateSqlQueryFromTemplate(String tableName, Map<String, Field> columnsMapping, Entity target);
}
