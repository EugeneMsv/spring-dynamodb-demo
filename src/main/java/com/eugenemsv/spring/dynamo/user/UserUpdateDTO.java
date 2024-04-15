package com.eugenemsv.spring.dynamo.user;

import java.time.Year;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class UserUpdateDTO {
  private final String firstName;

  private final Year birthYear;

  private final String email;

  private final String lastName;

  private final Integer version;
}
