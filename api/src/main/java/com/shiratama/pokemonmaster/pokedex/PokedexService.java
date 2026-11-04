package com.shiratama.pokemonmaster.pokedex;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiratama.pokemonmaster.dto.PokedexDetailResponse;
import com.shiratama.pokemonmaster.repository.Pokemon;
import com.shiratama.pokemonmaster.repository.PokemonTypesRepository;
import com.shiratama.pokemonmaster.repository.PokemonsRepository;

@Service
@RequiredArgsConstructor
public class PokedexService {
  private final PokemonsRepository pokemonsRepository;
  private final PokemonTypesRepository pokemonTypesRepository;

  @Transactional(readOnly = true)
  public List<Pokemon> search(String name, Integer pokemonId, Integer typeId) {
    Specification<Pokemon> criteria = (root, query, cb) -> {
      List<Predicate> conditions = new ArrayList<>();

      if (name != null && !name.isBlank()) {
        conditions.add(cb.like(root.get("name"), "%" + name.trim() + "%"));
      }
      if (pokemonId != null) {
        conditions.add(cb.equal(root.get("pokemonId"), pokemonId));
      }
      if (typeId != null) {
        conditions.add(cb.or(
            cb.equal(root.get("type1Id"), typeId),
            cb.equal(root.get("type2Id"), typeId)));
      }

      return cb.and(conditions.toArray(new Predicate[0]));
    };

    return pokemonsRepository.findAll(
        criteria, Sort.by("pokemonId").ascending());
  }

  @Transactional(readOnly = true)
  public Optional<PokedexDetailResponse> findDetail(Integer pokemonId) {
    return pokemonsRepository.findById(pokemonId).map(pokemon -> {
      List<String> types = new ArrayList<>();
      types.add(typeName(pokemon.getType1Id()));

      if (pokemon.getType2Id() != null) {
        types.add(typeName(pokemon.getType2Id()));
      }

      return new PokedexDetailResponse(
          pokemon.getPokemonId(),
          pokemon.getName(),
          "/image/" + pokemon.getPokemonId() + ".png",
          pokemon.getGender(),
          pokemon.getCategory(),
          types,
          pokemon.getHeight(),
          pokemon.getWeight(),
          new PokedexDetailResponse.BaseStats(
              pokemon.getBaseHp(),
              pokemon.getBaseAttack(),
              pokemon.getBaseDefense(),
              pokemon.getBaseSpecialAttack(),
              pokemon.getBaseSpecialDefense(),
              pokemon.getBaseSpeed()));
    });
  }

  private String typeName(Integer typeId) {
    return pokemonTypesRepository.findById(typeId)
        .orElseThrow(() -> new IllegalStateException("Unknown type ID: " + typeId))
        .getTypeName();
  }
}