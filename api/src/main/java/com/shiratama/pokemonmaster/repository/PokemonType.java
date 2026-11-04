package com.shiratama.pokemonmaster.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "types")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PokemonType {
  @Id
  @Column(name = "type_id")
  private Integer typeId;

  @Column(name = "type_name", nullable = false, length = 5)
  private String typeName;
}