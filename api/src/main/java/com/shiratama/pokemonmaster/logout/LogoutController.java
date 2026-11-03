package com.shiratama.pokemonmaster.logout;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logout")
public class LogoutController {

  @Value("${app.cookie.secure}")
  private boolean cookieSecure;

  @PostMapping
  public ResponseEntity<Void> logout() {
    ResponseCookie cookie = ResponseCookie.from("JWT", "")
        .httpOnly(true)
        .secure(cookieSecure)
        .sameSite("Lax")
        .path("/")
        .maxAge(Duration.ZERO)
        .build();

    return ResponseEntity.noContent()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .cacheControl(CacheControl.noStore())
        .build();
  }
}