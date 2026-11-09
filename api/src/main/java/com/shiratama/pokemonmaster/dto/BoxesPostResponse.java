package com.shiratama.pokemonmaster.dto;

import com.shiratama.pokemonmaster.repository.Box;

public record BoxesPostResponse(Integer boxId) {
  public static BoxesPostResponse from(Box box) {
    return new BoxesPostResponse(box.getBoxId());
  }
}