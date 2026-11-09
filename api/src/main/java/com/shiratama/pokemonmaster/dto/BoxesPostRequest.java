package com.shiratama.pokemonmaster.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BoxesPostRequest(
    @NotNull(message = "図鑑番号を入力してください。") @Min(value = 1, message = "図鑑番号は1以上で入力してください。") @Max(value = 9999, message = "図鑑番号は4桁以内で入力してください。") Integer pokemonId,

    @Pattern(regexp = "^[Ａ-Ｚａ-ｚ０-９\\u3041-\\u3094\\u30A1-\\u30FA]{1,6}$", message = "ニックネームは、ひらがな・カタカナ・全角英数字を使用し、1〜6文字で入力してください。") String nickname,

    @NotNull(message = "性別を選択してください。") @Min(value = 1, message = "性別は1〜3から選択してください。") @Max(value = 3, message = "性別は1〜3から選択してください。") Integer gender) {

  public BoxesPostRequest {
    if (nickname != null && nickname.isBlank()) {
      nickname = null;
    }
  }
}
