package com.eugenemsv.spring.dynamo.user;

import java.time.Year;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  @PostMapping
  public ResponseEntity<UserReadingDTO> create(@RequestBody UserCreationDTO userCreationDTO) {
    User newUser = userMapper.map(userCreationDTO);
    userService.create(newUser);
    return userService
        .findOne(newUser.getFirstName(), newUser.getBirthYear(), newUser.getEmail())
        .map(userMapper::map)
        .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.CREATED).build());
  }

  @GetMapping
  public ResponseEntity<UserReadingDTO> getOne(
      @RequestParam("firstName") String firstName,
      @RequestParam("birthYear") Year birthYear,
      @RequestParam("email") String email) {
    return userService
        .findOne(firstName, birthYear, email)
        .map(userMapper::map)
        .map(dto -> ResponseEntity.status(HttpStatus.OK).body(dto))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PutMapping
  public ResponseEntity<UserReadingDTO> update(@RequestBody UserUpdateDTO userUpdateDTO) {
    return userService
        .update(userMapper.map(userUpdateDTO))
        .map(userMapper::map)
        .map(dto -> ResponseEntity.status(HttpStatus.OK).body(dto))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @DeleteMapping
  public ResponseEntity<UserReadingDTO> delete(
      @RequestParam("firstName") String firstName,
      @RequestParam("birthYear") Year birthYear,
      @RequestParam("email") String email) {
    return userService
        .delete(firstName, birthYear, email)
        .map(userMapper::map)
        .map(dto -> ResponseEntity.status(HttpStatus.NO_CONTENT).body(dto))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }
}
