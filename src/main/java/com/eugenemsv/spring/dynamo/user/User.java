package com.eugenemsv.spring.dynamo.user;

import java.time.Year;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbVersionAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbImmutable(builder = User.UserBuilder.class)
@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode(of = {"firstName", "lastName", "email"})
public class User {

  @Getter(onMethod_ = @DynamoDbSecondaryPartitionKey(indexNames = "users_by_full_name"))
  private final String firstName;

  @Getter(onMethod_ = @DynamoDbAttribute("birthYear"))
  private final Year birthYear;

  @Getter(onMethod_ = @DynamoDbPartitionKey)
  private final String key = buildKey(firstName, birthYear);

  @Getter(onMethod_ = @DynamoDbSortKey)
  private final String email;

  @Getter(onMethod_ = @DynamoDbSecondarySortKey(indexNames = "users_by_full_name"))
  private final String lastName;

  @Getter(onMethod_ = @DynamoDbVersionAttribute)
  private final Integer version;

  static String buildKey(String firstName, Year birthYear) {
    return firstName + "_" + birthYear;
  }
}
