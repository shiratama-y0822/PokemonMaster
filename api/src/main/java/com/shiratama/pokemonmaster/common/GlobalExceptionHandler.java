package com.shiratama.pokemonmaster.common;

import com.shiratama.pokemonmaster.user.DuplicateUserIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DuplicateUserIdException.class)
  ProblemDetail handleDuplicateUserId(DuplicateUserIdException e) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
    problem.setTitle("Duplicate user ID");
    return problem;
  }
}