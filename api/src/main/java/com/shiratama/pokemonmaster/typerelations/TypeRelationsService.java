package com.shiratama.pokemonmaster.typerelations;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiratama.pokemonmaster.dto.TypeRelationResponse;
import com.shiratama.pokemonmaster.repository.TypeRelationsRepository;

@Service
@RequiredArgsConstructor
public class TypeRelationsService {
  private final TypeRelationsRepository typeRelationsRepository;

  @Transactional(readOnly = true)
  public List<TypeRelationResponse> findAll() {
    return typeRelationsRepository
        .findAll(Sort.by("attackTypeId", "defenceTypeId"))
        .stream()
        .map(TypeRelationResponse::from)
        .toList();
  }
}