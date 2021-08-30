package com.bank.infrastructure.generator.sql.spi;

import com.bank.domain.db.AccountEntity;

import java.lang.reflect.Field;
import java.util.Map;

public interface SqlGenerator<Entity> {
    String generateSqlQueryFromTemplate(String tableName, Map<String, Field> columnsMapping, Entity target);
}
