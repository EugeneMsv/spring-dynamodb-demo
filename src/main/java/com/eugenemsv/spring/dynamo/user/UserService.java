package com.eugenemsv.spring.dynamo.user;

import java.time.Year;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserService {
  private final UserRepository repository;

  void create(User newUser) {
    repository.create(newUser);
  }

  Optional<User> findOne(String firstName, Year birthYear, String email) {
    return Optional.ofNullable(repository.findOne(User.buildKey(firstName, birthYear), email));
  }

  Optional<User> update(User forUpdate) {
    return Optional.ofNullable(repository.update(forUpdate));
  }

  Optional<User> delete(String firstName, Year birthYear, String email) {
    return Optional.ofNullable(repository.delete(User.buildKey(firstName, birthYear), email));
  }
}
