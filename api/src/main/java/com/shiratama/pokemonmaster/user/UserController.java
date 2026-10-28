package com.shiratama.pokemonmaster.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserResponse> create(@Valid @RequestBody UsersPostRequest request) {
    User user = userService.create(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(user));
  }
}