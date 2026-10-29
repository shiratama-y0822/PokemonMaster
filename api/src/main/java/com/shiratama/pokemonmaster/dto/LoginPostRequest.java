package com.shiratama.pokemonmaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginPostRequest(
    @NotBlank @Pattern(regexp = "^[A-Za-z0-9_@-]{1,16}$", message = "ユーザーIDは、半角英数字・記号（_ @ -）を使用し、1〜16文字で入力してください。") String userId,

    @NotBlank @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!?_-])[a-zA-Z0-9!?_-]{8,24}$", message = "パスワードは、半角英大文字・半角英小文字・半角数字・記号（! ? _ -）をそれぞれ1文字以上使用し、8〜24文字で入力してください。") String password) {

  @Override
  public String toString() {
    return "LoginPostRequest[userId=%s, password=****]".formatted(userId);
  }
}
