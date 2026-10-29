package com.shiratama.pokemonmaster.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shiratama.pokemonmaster.exception.DuplicateUserIdException;
import com.shiratama.pokemonmaster.exception.LoginFailedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DuplicateUserIdException.class)
  ProblemDetail handleDuplicateUserId(DuplicateUserIdException e) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
    problem.setTitle("Duplicate user ID");
    return problem;
  }

  @ExceptionHandler(LoginFailedException.class)
  ProblemDetail handleLoginFailed(LoginFailedException e) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
    problem.setTitle("Login failed");
    return problem;
  }

}