package com.shiratama.pokemonmaster.repository;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode
public class TypeRelationId implements Serializable {
  private Integer attackTypeId;
  private Integer defenceTypeId;
}