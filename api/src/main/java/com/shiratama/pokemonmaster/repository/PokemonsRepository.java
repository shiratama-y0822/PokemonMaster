package com.shiratama.pokemonmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PokemonsRepository
    extends JpaRepository<Pokemon, Integer>, JpaSpecificationExecutor<Pokemon> {
}