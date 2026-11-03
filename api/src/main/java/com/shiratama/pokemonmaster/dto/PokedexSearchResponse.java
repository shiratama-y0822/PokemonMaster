package com.shiratama.pokemonmaster.dto;

import com.shiratama.pokemonmaster.repository.Pokemon;

public record PokedexSearchResponse(
    Integer pokemonId,
    String name,
    String image) {
  public static PokedexSearchResponse from(Pokemon pokemon) {
    return new PokedexSearchResponse(
        pokemon.getPokemonId(),
        pokemon.getName(),
        "image/" + pokemon.getPokemonId() + ".png");
  }
}