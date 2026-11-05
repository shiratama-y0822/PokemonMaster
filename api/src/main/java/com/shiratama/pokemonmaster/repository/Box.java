package com.shiratama.pokemonmaster.repository;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "boxes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Box {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "box_id")
  private Integer boxId;

  @Column(name = "user_id", nullable = false, length = 16)
  private String userId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "pokemon_id", nullable = false)
  private Pokemon pokemon;

  @Column(name = "nickname", length = 6)
  private String nickname;

  @Column(name = "gender", nullable = false)
  private Integer gender;
}