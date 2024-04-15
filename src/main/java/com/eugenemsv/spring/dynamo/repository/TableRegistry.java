package com.eugenemsv.spring.dynamo.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Component
public class TableRegistry {

  private final Map<Class<?>, DynamoDbTable<?>> registeredTables = new HashMap<>();

  public <T> void registerTable(Class<T> tableClass, DynamoDbTable<T> dynamoDbTable) {
    registeredTables.put(tableClass, dynamoDbTable);
  }

  public <T> DynamoDbTable<T> tableFor(Class<T> tableClass) {
    return (DynamoDbTable<T>) registeredTables.get(tableClass);
  }
}
