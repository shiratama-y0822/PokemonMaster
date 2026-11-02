package com.shiratama.pokemonmaster.login;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiratama.pokemonmaster.dto.LoginPostRequest;
import com.shiratama.pokemonmaster.dto.LoginPostResponse;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;
  private final JwtService jwtService;

  @Value("${app.jwt.ttl}")
  private String jwtTtl;

  @Value("${app.cookie.secure}")
  private boolean cookieSecure;

  @PostMapping
  public ResponseEntity<LoginPostResponse> login(
      @Valid @RequestBody LoginPostRequest request) {

    // 1. ユーザーIDとパスワードを照合する
    LoginPostResponse response = loginService.login(request);

    // 2. 認証成功後にJWTを発行する
    String jwt = jwtService.issueToken(request.userId());

    // 3. JWTを保存するCookieを作成する
    ResponseCookie cookie = ResponseCookie.from("JWT", jwt)
        .httpOnly(true)
        .secure(cookieSecure)
        .sameSite("Lax")
        .path("/")
        .maxAge(DurationStyle.detectAndParse(jwtTtl))
        .build();

    // 4. Cookieをヘッダー、ニックネームを本文に設定して返す
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .cacheControl(CacheControl.noStore())
        .body(response);
  }
}