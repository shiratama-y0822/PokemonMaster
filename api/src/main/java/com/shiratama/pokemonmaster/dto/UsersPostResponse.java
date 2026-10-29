package com.shiratama.pokemonmaster.dto;

import com.shiratama.pokemonmaster.repository.User;

public record UsersPostResponse(String userId, String nickname) {

  public static UsersPostResponse from(User user) {
    return new UsersPostResponse(user.getUserId(), user.getNickname());
  }
}