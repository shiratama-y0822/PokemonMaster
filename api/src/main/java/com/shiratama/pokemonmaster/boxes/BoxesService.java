package com.shiratama.pokemonmaster.boxes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.shiratama.pokemonmaster.dto.BoxesDetailResponse;
import com.shiratama.pokemonmaster.dto.BoxesListResponse;
import com.shiratama.pokemonmaster.dto.BoxesPatchRequest;
import com.shiratama.pokemonmaster.dto.BoxesPostRequest;
import com.shiratama.pokemonmaster.repository.Box;
import com.shiratama.pokemonmaster.repository.BoxesRepository;
import com.shiratama.pokemonmaster.repository.Pokemon;
import com.shiratama.pokemonmaster.repository.PokemonTypesRepository;
import com.shiratama.pokemonmaster.repository.PokemonsRepository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

@Service
@RequiredArgsConstructor
public class BoxesService {
  private final BoxesRepository boxesRepository;
  private final PokemonsRepository pokemonsRepository;
  private final PokemonTypesRepository pokemonTypesRepository;

  @Transactional(readOnly = true)
  public List<Box> findAllByUserId(String userId) {
    return boxesRepository.findByUserIdOrderByBoxIdAsc(userId);
  }

  @Transactional(readOnly = true)
  public List<BoxesListResponse> search(
      String userId, String name, Integer pokemonId, Integer typeId) {

    Specification<Box> criteria = (root, query, cb) -> {
      List<Predicate> conditions = new ArrayList<>();
      Join<Box, Pokemon> pokemon = root.join("pokemon");

      conditions.add(cb.equal(root.get("userId"), userId));

      if (name != null && !name.isBlank()) {
        String keyword = "%" + name.trim() + "%";
        conditions.add(cb.or(
            cb.like(pokemon.get("name"), keyword),
            cb.like(root.get("nickname"), keyword)));
      }
      if (pokemonId != null) {
        conditions.add(cb.equal(pokemon.get("pokemonId"), pokemonId));
      }
      if (typeId != null) {
        conditions.add(cb.or(
            cb.equal(pokemon.get("type1Id"), typeId),
            cb.equal(pokemon.get("type2Id"), typeId)));
      }

      return cb.and(conditions.toArray(new Predicate[0]));
    };

    return boxesRepository.findAll(
        criteria, Sort.by("boxId").ascending()).stream()
        .map(BoxesListResponse::from)
        .toList();
  }

  @Transactional
  public Box create(String userId, BoxesPostRequest request) {
    Pokemon pokemon = pokemonsRepository.findById(request.pokemonId())
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.BAD_REQUEST, "存在しない図鑑番号です。"));

    boolean allowedGender = switch (pokemon.getGender()) {
      case 1, 2, 3 -> pokemon.getGender().equals(request.gender());
      case 4 -> Integer.valueOf(1).equals(request.gender())
          || Integer.valueOf(2).equals(request.gender());
      default -> false;
    };

    if (!allowedGender) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "このポケモンに指定できない性別です。");
    }

    String savedNickname = request.nickname() == null
        ? pokemon.getName()
        : request.nickname();

    return boxesRepository.save(
        new Box(userId, pokemon, savedNickname, request.gender()));

  }

  @Transactional
  public Box update(String userId, Integer boxId, BoxesPatchRequest request) {
    if (!request.hasAnyField()) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "変更する項目を指定してください。");
    }
    if (request.isPokemonIdSpecified() && request.getPokemonId() == null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "図鑑番号にnullは指定できません。");
    }
    if (request.isGenderSpecified() && request.getGender() == null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "性別にnullは指定できません。");
    }

    Box box = boxesRepository.findByBoxIdAndUserId(boxId, userId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "指定されたボックスが見つかりません。"));

    Pokemon pokemon = request.isPokemonIdSpecified()
        ? pokemonsRepository.findById(request.getPokemonId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "存在しない図鑑番号です。"))
        : box.getPokemon();

    Integer gender = request.isGenderSpecified()
        ? request.getGender()
        : box.getGender();

    boolean allowedGender = switch (pokemon.getGender()) {
      case 1, 2, 3 -> pokemon.getGender().equals(gender);
      case 4 -> Integer.valueOf(1).equals(gender)
          || Integer.valueOf(2).equals(gender);
      default -> false;
    };
    if (!allowedGender) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "このポケモンに指定できない性別です。");
    }

    String nickname = request.isNicknameSpecified()
        ? (request.getNickname() == null
            ? pokemon.getName()
            : request.getNickname())
        : box.getNickname();

    box.update(pokemon, nickname, gender);
    return box;
  }

  @Transactional
  public void delete(String userId, Integer boxId) {
    Box box = boxesRepository.findByBoxIdAndUserId(boxId, userId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "指定されたボックスが見つかりません。"));

    boxesRepository.delete(box);
  }

  @Transactional(readOnly = true)
  public Optional<BoxesDetailResponse> findDetail(String userId, Integer boxId) {
    return boxesRepository.findByBoxIdAndUserId(boxId, userId)
        .map(box -> {
          Pokemon pokemon = box.getPokemon();

          List<String> types = new ArrayList<>();
          types.add(typeName(pokemon.getType1Id()));
          if (pokemon.getType2Id() != null) {
            types.add(typeName(pokemon.getType2Id()));
          }

          return new BoxesDetailResponse(
              pokemon.getPokemonId(),
              pokemon.getName(),
              "/image/" + pokemon.getPokemonId() + ".png",
              box.getBoxId(),
              box.getNickname(),
              box.getGender(),
              pokemon.getCategory(),
              types,
              pokemon.getHeight(),
              pokemon.getWeight(),
              new BoxesDetailResponse.BaseStats(
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