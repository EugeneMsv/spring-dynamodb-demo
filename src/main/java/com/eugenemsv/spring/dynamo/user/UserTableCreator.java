package com.eugenemsv.spring.dynamo.user;

import com.eugenemsv.spring.dynamo.repository.TableRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Component
@RequiredArgsConstructor
public class UserTableCreator implements ApplicationRunner {

  private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

  private final TableRegistry tableRegistry;

  @Override
  public void run(ApplicationArguments args) {
    DynamoDbTable<User> userTable =
        dynamoDbEnhancedClient.table(User.class.getSimpleName(), TableSchema.fromBean(User.class));
    userTable.createTable();
    tableRegistry.registerTable(User.class, userTable);
  }
}
