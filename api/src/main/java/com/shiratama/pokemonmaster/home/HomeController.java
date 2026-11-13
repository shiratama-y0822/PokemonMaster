package com.shiratama.pokemonmaster.home;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiratama.pokemonmaster.dto.HomeResponse;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

  private final HomeService homeService;

  @GetMapping
  public HomeResponse getHome(@AuthenticationPrincipal Jwt jwt) {
    return homeService.getHome(jwt.getSubject());
  }
}