package com.shiratama.pokemonmaster.typerelations;

import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiratama.pokemonmaster.dto.TypeRelationResponse;

@RestController
@RequestMapping("/api/typeaisyou")
@RequiredArgsConstructor
public class TypeRelationsController {
  private final TypeRelationsService typeRelationsService;

  @GetMapping
  public List<TypeRelationResponse> findAll() {
    return typeRelationsService.findAll();
  }
}