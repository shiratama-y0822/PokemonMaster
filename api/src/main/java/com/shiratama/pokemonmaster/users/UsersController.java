package com.shiratama.pokemonmaster.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shiratama.pokemonmaster.dto.UsersPostResponse;
import com.shiratama.pokemonmaster.dto.UsersPostRequest;
import com.shiratama.pokemonmaster.repository.User;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

  private final UsersService userService;

  @PostMapping
  public ResponseEntity<UsersPostResponse> create(@Valid @RequestBody UsersPostRequest request) {
    User user = userService.create(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(UsersPostResponse.from(user));
  }
}