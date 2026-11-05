package com.shiratama.pokemonmaster.boxes;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiratama.pokemonmaster.dto.BoxListResponse;

@RestController
@RequestMapping("/api/box")
@RequiredArgsConstructor
public class BoxesController {
  private final BoxesService boxService;

  @GetMapping
  public List<BoxListResponse> list(@AuthenticationPrincipal Jwt jwt) {
    return boxService.findAllByUserId(jwt.getSubject()).stream()
        .map(BoxListResponse::from)
        .toList();
  }
}