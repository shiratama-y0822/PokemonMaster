package com.shiratama.pokemonmaster.boxes;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shiratama.pokemonmaster.repository.Box;
import com.shiratama.pokemonmaster.repository.BoxesRepository;

@Service
@RequiredArgsConstructor
public class BoxesService {
  private final BoxesRepository boxesRepository;

  @Transactional(readOnly = true)
  public List<Box> findAllByUserId(String userId) {
    return boxesRepository.findByUserIdOrderByBoxIdAsc(userId);
  }
}