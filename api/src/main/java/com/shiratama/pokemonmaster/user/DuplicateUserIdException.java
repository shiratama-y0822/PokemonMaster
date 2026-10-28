package com.shiratama.pokemonmaster.user;

public class DuplicateUserIdException extends RuntimeException {

  public DuplicateUserIdException(String userId) {
    super("userIdが重複しています" + userId);
  }
}