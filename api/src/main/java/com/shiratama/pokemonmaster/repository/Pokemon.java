package com.shiratama.pokemonmaster.repository;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pokemons")

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pokemon {
  @Id
  @Column(name = "pokemon_id")
  private Integer pokemonId;

  @Column(name = "name", nullable = false, length = 32)
  private String name;

  @Column(name = "gender", nullable = false)
  private Integer gender;

  @Column(name = "type1_id", nullable = false)
  private Integer type1Id;

  @Column(name = "type2_id")
  private Integer type2Id;

  @Column(name = "category", nullable = false, length = 32)
  private String category;

  @Column(name = "height", nullable = false, precision = 4, scale = 1)
  private BigDecimal height;

  @Column(name = "weight", nullable = false, precision = 4, scale = 1)
  private BigDecimal weight;

  @Column(name = "base_hp", nullable = false)
  private Integer baseHp;

  @Column(name = "base_attack", nullable = false)
  private Integer baseAttack;

  @Column(name = "base_defense", nullable = false)
  private Integer baseDefense;

  @Column(name = "base_special_attack", nullable = false)
  private Integer baseSpecialAttack;

  @Column(name = "base_special_defense", nullable = false)
  private Integer baseSpecialDefense;

  @Column(name = "base_speed", nullable = false)
  private Integer baseSpeed;
}