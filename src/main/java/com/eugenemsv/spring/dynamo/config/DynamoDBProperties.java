package com.eugenemsv.spring.dynamo.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.dynamodb")
@Getter
@RequiredArgsConstructor
public class DynamoDBProperties {

  private final String region;

  private final String endpoint;
}
