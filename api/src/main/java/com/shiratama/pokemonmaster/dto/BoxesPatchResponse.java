package com.shiratama.pokemonmaster.dto;

import com.shiratama.pokemonmaster.repository.Box;

public record BoxesPatchResponse(Integer boxId) {
  public static BoxesPatchResponse from(Box box) {
    return new BoxesPatchResponse(box.getBoxId());
  }
}