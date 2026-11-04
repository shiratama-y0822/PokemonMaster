package com.shiratama.pokemonmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonTypesRepository
        extends JpaRepository<PokemonType, Integer> {
}