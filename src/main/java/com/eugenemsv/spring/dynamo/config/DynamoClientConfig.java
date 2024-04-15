package com.eugenemsv.spring.dynamo.config;

import java.net.URI;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
@EnableConfigurationProperties(DynamoDBProperties.class)
public class DynamoClientConfig {

  @Bean
  public DynamoDbClient dynamoDbClient(DynamoDBProperties dynamoDBProperties) {

    return DynamoDbClient.builder()
        .region(Region.of(dynamoDBProperties.getRegion()))
        .endpointOverride(URI.create(dynamoDBProperties.getEndpoint()))
        .credentialsProvider(ProfileCredentialsProvider.create())
        .build();
  }

  @Bean
  public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
    return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
  }
}
