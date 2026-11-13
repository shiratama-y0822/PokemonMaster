package com.shiratama.pokemonmaster.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.util.StringUtils;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      JwtDecoder jwtDecoder) throws Exception {

    // JWTの検証を担当する部品
    JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtDecoder);

    ProviderManager authenticationManager = new ProviderManager(provider);

    // CookieからJWTを取り出す処理を設定する
    BearerTokenAuthenticationConverter converter = new BearerTokenAuthenticationConverter();

    converter.setBearerTokenResolver(this::resolveJwtCookie);

    // 未認証・JWT不正の場合は401を返す
    AuthenticationEntryPoint entryPoint = new BearerTokenAuthenticationEntryPoint();

    // Spring標準のJWT認証フィルターを用意する
    BearerTokenAuthenticationFilter jwtFilter = new BearerTokenAuthenticationFilter(
        authenticationManager, converter);

    jwtFilter.setAuthenticationEntryPoint(entryPoint);
    jwtFilter.setSecurityContextRepository(
        new RequestAttributeSecurityContextRepository());

    http
        .csrf(csrf -> csrf.disable())

        .sessionManagement(session -> session
            .sessionCreationPolicy(
                SessionCreationPolicy.STATELESS))

        .exceptionHandling(exception -> exception
            .authenticationEntryPoint(entryPoint))

        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                HttpMethod.POST,
                "/api/login",
                "/api/users")
            .permitAll()
            .requestMatchers(HttpMethod.GET, "/api/typeaisyou").permitAll()
            .requestMatchers("/error").permitAll()
            .anyRequest().authenticated())

        .addFilterBefore(
            jwtFilter,
            UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  // CookieからJWTを取り出す
  private String resolveJwtCookie(HttpServletRequest request) {

    String path = request.getServletPath();
    String method = request.getMethod();

    if (("POST".equals(method)
        && ("/api/login".equals(path)
            || "/api/users".equals(path)))
        || ("GET".equals(method) && "/api/typeaisyou".equals(path))
        || "/error".equals(path)) {
      return null;
    }

    Cookie[] cookies = request.getCookies();

    if (cookies == null) {
      return null;
    }

    String token = null;

    for (Cookie cookie : cookies) {
      if ("JWT".equals(cookie.getName())) {

        // 同名Cookieの重複や空の値は不正として扱う
        if (token != null
            || !StringUtils.hasText(cookie.getValue())) {
          throw new OAuth2AuthenticationException(
              new OAuth2Error("invalid_token"));
        }

        token = cookie.getValue();
      }
    }

    return token;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}