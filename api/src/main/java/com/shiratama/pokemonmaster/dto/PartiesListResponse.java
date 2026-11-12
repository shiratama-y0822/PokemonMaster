package com.shiratama.pokemonmaster.dto;

import java.util.List;

public record PartiesListResponse(
    Integer partyId,
    String partyName,
    List<Member> members) {

  public record Member(
      Integer boxId,
      int position,
      String pokemonName,
      String image) {
  }
}