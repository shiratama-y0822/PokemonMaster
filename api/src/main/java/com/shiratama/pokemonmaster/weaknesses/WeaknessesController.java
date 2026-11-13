package com.shiratama.pokemonmaster.weaknesses;

import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiratama.pokemonmaster.dto.WeaknessResponse;

@RestController
@RequestMapping("/api/{pokemonId}/weaknesses")
@RequiredArgsConstructor
public class WeaknessesController {
  private final WeaknessesService weaknessesService;

  @GetMapping
  public ResponseEntity<List<WeaknessResponse>> findByPokemonId(
      @PathVariable Integer pokemonId) {
    return weaknessesService.findByPokemonId(pokemonId)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}