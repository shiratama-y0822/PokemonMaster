package com.shiratama.pokemonmaster.repository;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "type_relations")
@IdClass(TypeRelationId.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TypeRelation {
  @Id
  @Column(name = "attack_type_id")
  private Integer attackTypeId;

  @Id
  @Column(name = "defence_type_id")
  private Integer defenceTypeId;

  @Column(name = "multiplier", nullable = false, precision = 3, scale = 1)
  private BigDecimal multiplier;
}