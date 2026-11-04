package com.shiratama.pokemonmaster.pokedex;

import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shiratama.pokemonmaster.dto.PokedexDetailResponse;
import com.shiratama.pokemonmaster.dto.PokedexSearchResponse;

@RestController
@RequestMapping("/api/pokedex")
@RequiredArgsConstructor
public class PokedexController {
  private final PokedexService pokedexService;

  @GetMapping
  public List<PokedexSearchResponse> search(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer pokemonId,
      @RequestParam(required = false) Integer typeId) {

    return pokedexService.search(name, pokemonId, typeId).stream()
        .map(PokedexSearchResponse::from)
        .toList();
  }

  @GetMapping("/{pokemonId}")
  public ResponseEntity<PokedexDetailResponse> detail(
      @PathVariable Integer pokemonId) {
    return pokedexService.findDetail(pokemonId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.noContent().build());
  }
}