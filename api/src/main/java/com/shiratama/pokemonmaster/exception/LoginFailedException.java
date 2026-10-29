package com.shiratama.pokemonmaster.exception;

public class LoginFailedException extends RuntimeException {

  public LoginFailedException() {
    super("ユーザーIDまたはパスワードが違います");
  }
}