package com.shiratama.pokemonmaster.dto;

import java.math.BigDecimal;

import com.shiratama.pokemonmaster.repository.TypeRelation;

public record TypeRelationResponse(
    Integer attackTypeId,
    Integer defenceTypeId,
    BigDecimal multiplier) {

  public static TypeRelationResponse from(TypeRelation relation) {
    return new TypeRelationResponse(
        relation.getAttackTypeId(),
        relation.getDefenceTypeId(),
        relation.getMultiplier());
  }
}