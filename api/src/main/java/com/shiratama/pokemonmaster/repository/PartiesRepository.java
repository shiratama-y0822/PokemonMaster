package com.shiratama.pokemonmaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartiesRepository
    extends JpaRepository<Party, Integer> {
  @EntityGraph(attributePaths = {
      "position1.pokemon",
      "position2.pokemon",
      "position3.pokemon",
      "position4.pokemon",
      "position5.pokemon",
      "position6.pokemon"
  })
  List<Party> findByUserIdOrderByPartyIdAsc(String userId);
}