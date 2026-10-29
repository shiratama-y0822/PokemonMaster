package com.shiratama.pokemonmaster.login;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shiratama.pokemonmaster.dto.LoginPostRequest;
import com.shiratama.pokemonmaster.dto.LoginPostResponse;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;

  @PostMapping
  public ResponseEntity<LoginPostResponse> login(@Valid @RequestBody LoginPostRequest request) {
    LoginPostResponse response = loginService.login(request);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}