package com.shiratama.pokemonmaster.user;

public record UserResponse(String userId, String nickname) {

  static UserResponse from(User user) {
    return new UserResponse(user.getUserId(), user.getNickname());
  }
}