package com.shiratama.pokemonmaster.dto;

import java.math.BigDecimal;
import java.util.List;

public record PokedexDetailResponse(
    Integer pokemonId,
    String name,
    String image,
    Integer gender,
    String category,
    List<String> type,
    BigDecimal height,
    BigDecimal weight,
    BaseStats baseStats) {

  public record BaseStats(
      Integer hp,
      Integer attack,
      Integer defense,
      Integer specialAttack,
      Integer specialDefense,
      Integer speed) {
  }
}