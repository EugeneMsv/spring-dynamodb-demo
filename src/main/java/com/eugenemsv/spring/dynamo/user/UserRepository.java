package com.eugenemsv.spring.dynamo.user;

import com.eugenemsv.spring.dynamo.repository.TableRegistry;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Component
@RequiredArgsConstructor
public class UserRepository {

  private final TableRegistry tableRegistry;

  private DynamoDbTable<User> getTable() {
    return tableRegistry.tableFor(User.class);
  }

  void create(User newUser) {
    getTable().putItem(newUser);
  }

  User findOne(String key, String email) {
    return getTable().getItem(Key.builder().partitionValue(key).sortValue(email).build());
  }

  User update(User forUpdate) {
    return getTable()
        .updateItem(
            UpdateItemEnhancedRequest.builder(User.class)
                .item(forUpdate)
                .conditionExpression(optimisitcLockingExpression(forUpdate.getVersion()))
                .build());
  }

  User delete(String key, String email) {
    return getTable().deleteItem(Key.builder().partitionValue(key).sortValue(email).build());
  }

  private Expression optimisitcLockingExpression(Integer currentEntityVersion) {
    return currentEntityVersion == null
        ? Expression.builder()
            .expression("attribute_exists(key) AND attribute_not_exists(version)")
            .build()
        : Expression.builder()
            .expression("attribute_exists(key) AND version = :version")
            .expressionValues(
                Map.of(
                    ":version",
                    AttributeValue.builder().n(currentEntityVersion.toString()).build()))
            .build();
  }
}
