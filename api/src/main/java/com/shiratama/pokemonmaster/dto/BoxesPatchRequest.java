package com.shiratama.pokemonmaster.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class BoxesPatchRequest {
  @Min(value = 1, message = "図鑑番号は1以上で入力してください。")
  @Max(value = 9999, message = "図鑑番号は4桁以内で入力してください。")
  private Integer pokemonId;

  @Pattern(regexp = "^[Ａ-Ｚａ-ｚ０-９\\u3041-\\u3094\\u30A1-\\u30FA]{1,6}$", message = "ニックネームは、ひらがな・カタカナ・全角英数字を使用し、1〜6文字で入力してください。")
  private String nickname;

  @Min(value = 1, message = "性別は1〜3から選択してください。")
  @Max(value = 3, message = "性別は1〜3から選択してください。")
  private Integer gender;

  private boolean pokemonIdSpecified;
  private boolean nicknameSpecified;
  private boolean genderSpecified;

  @JsonSetter("pokemonId")
  public void setPokemonId(Integer pokemonId) {
    this.pokemonIdSpecified = true;
    this.pokemonId = pokemonId;
  }

  @JsonSetter("nickname")
  public void setNickname(String nickname) {
    this.nicknameSpecified = true;
    this.nickname = nickname != null && nickname.isBlank() ? null : nickname;
  }

  @JsonSetter("gender")
  public void setGender(Integer gender) {
    this.genderSpecified = true;
    this.gender = gender;
  }

  public boolean hasAnyField() {
    return pokemonIdSpecified || nicknameSpecified || genderSpecified;
  }
}
