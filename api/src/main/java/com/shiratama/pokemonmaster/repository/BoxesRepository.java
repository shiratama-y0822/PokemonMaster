package com.shiratama.pokemonmaster.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxesRepository
    extends JpaRepository<Box, Integer> {
  @EntityGraph(attributePaths = "pokemon")
  List<Box> findByUserIdOrderByBoxIdAsc(String userId);

  @EntityGraph(attributePaths = "pokemon")
  Optional<Box> findByBoxIdAndUserId(Integer boxId, String userId);
}