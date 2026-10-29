package com.shiratama.pokemonmaster.dto;

import com.shiratama.pokemonmaster.repository.User;

public record LoginPostResponse(String nickname) {

  public static LoginPostResponse from(User user) {
    return new LoginPostResponse(user.getNickname());
  }
}