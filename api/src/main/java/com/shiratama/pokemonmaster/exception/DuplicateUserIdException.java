package com.shiratama.pokemonmaster.exception;

public class DuplicateUserIdException extends RuntimeException {

  public DuplicateUserIdException(String userId) {
    super("userIdが重複しています" + userId);
  }
}