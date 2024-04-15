package com.eugenemsv.spring.dynamo.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  User map(UserCreationDTO userCreationDTO) {
    return User.builder()
        .firstName(userCreationDTO.getFirstName())
        .lastName(userCreationDTO.getLastName())
        .birthYear(userCreationDTO.getBirthYear())
        .email(userCreationDTO.getEmail())
        .version(userCreationDTO.getVersion() == null ? 1 : userCreationDTO.getVersion())
        .build();
  }

  User map(UserUpdateDTO userUpdateDTO) {
    return User.builder()
        .firstName(userUpdateDTO.getFirstName())
        .lastName(userUpdateDTO.getLastName())
        .birthYear(userUpdateDTO.getBirthYear())
        .email(userUpdateDTO.getEmail())
        .version(userUpdateDTO.getVersion())
        .build();
  }

  UserReadingDTO map(User user) {
    return UserReadingDTO.builder()
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .birthYear(user.getBirthYear())
        .email(user.getEmail())
        .version(user.getVersion())
        .build();
  }
}
