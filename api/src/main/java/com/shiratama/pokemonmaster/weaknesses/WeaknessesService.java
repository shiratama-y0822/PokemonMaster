package com.shiratama.pokemonmaster.weaknesses;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiratama.pokemonmaster.dto.WeaknessResponse;
import com.shiratama.pokemonmaster.repository.Pokemon;
import com.shiratama.pokemonmaster.repository.PokemonType;
import com.shiratama.pokemonmaster.repository.PokemonTypesRepository;
import com.shiratama.pokemonmaster.repository.PokemonsRepository;
import com.shiratama.pokemonmaster.repository.TypeRelation;
import com.shiratama.pokemonmaster.repository.TypeRelationsRepository;

@Service
@RequiredArgsConstructor
public class WeaknessesService {
  private final PokemonsRepository pokemonsRepository;
  private final PokemonTypesRepository pokemonTypesRepository;
  private final TypeRelationsRepository typeRelationsRepository;

  @Transactional(readOnly = true)
  public Optional<List<WeaknessResponse>> findByPokemonId(Integer pokemonId) {
    Optional<Pokemon> foundPokemon = pokemonsRepository.findById(pokemonId);
    if (foundPokemon.isEmpty()) {
      return Optional.empty();
    }

    Pokemon pokemon = foundPokemon.get();
    List<TypeRelation> relations = typeRelationsRepository.findAll();
    List<PokemonType> attackTypes = pokemonTypesRepository.findAll(Sort.by("typeId"));

    List<WeaknessResponse> responses = attackTypes.stream()
        .map(attackType -> {
          Integer attackTypeId = attackType.getTypeId();
          BigDecimal multiplier = relationMultiplier(
              relations, attackTypeId, pokemon.getType1Id());

          if (pokemon.getType2Id() != null) {
            multiplier = multiplier.multiply(relationMultiplier(
                relations, attackTypeId, pokemon.getType2Id()));
          }

          return new WeaknessResponse(
              pokemonId, attackTypeId, multiplier);
        })
        .toList();

    return Optional.of(responses);
  }

  private BigDecimal relationMultiplier(
      List<TypeRelation> relations,
      Integer attackTypeId,
      Integer defenceTypeId) {
    return relations.stream()
        .filter(relation -> relation.getAttackTypeId().equals(attackTypeId)
            && relation.getDefenceTypeId().equals(defenceTypeId))
        .findFirst()
        .map(TypeRelation::getMultiplier)
        .orElseThrow(() -> new IllegalStateException(
            "Type relation not found: attackTypeId=" + attackTypeId
                + ", defenceTypeId=" + defenceTypeId));
  }
}