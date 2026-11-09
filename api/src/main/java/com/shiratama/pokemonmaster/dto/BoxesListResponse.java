package com.shiratama.pokemonmaster.dto;

import com.shiratama.pokemonmaster.repository.Box;

public record BoxesListResponse(
    Integer boxId,
    String name,
    String image) {
  public static BoxesListResponse from(Box box) {
    String nickname = box.getNickname();
    String name = nickname == null || nickname.isBlank()
        ? box.getPokemon().getName()
        : nickname;

    return new BoxesListResponse(
        box.getBoxId(),
        name,
        "/image/" + box.getPokemon().getPokemonId() + ".png");
  }
}