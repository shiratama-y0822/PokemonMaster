package com.shiratama.pokemonmaster.config;

import java.util.Base64;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
public class JwtConfig {

  // 1. 設定値から署名用の秘密鍵を作成する
  @Bean
  public SecretKey jwtSecretKey(
      @Value("${app.jwt.secret}") String secret) {

    byte[] keyBytes = Base64.getDecoder().decode(secret);

    if (keyBytes.length < 32) {
      throw new IllegalArgumentException(
          "JWTの秘密鍵はBase64デコード後に32バイト以上必要です");
    }

    return new SecretKeySpec(keyBytes, "HmacSHA256");
  }

  // 2. JWTを発行するための部品を作成する
  @Bean
  public JwtEncoder jwtEncoder(SecretKey jwtSecretKey) {

    return NimbusJwtEncoder.withSecretKey(jwtSecretKey)
        .algorithm(MacAlgorithm.HS256)
        .build();
  }

  // 3. JWTを検証するための部品を作成する
  @Bean
  public JwtDecoder jwtDecoder(
      SecretKey jwtSecretKey,
      @Value("${app.jwt.issuer}") String issuer,
      @Value("${app.jwt.audience}") String audience) {

    NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(jwtSecretKey)
        .macAlgorithm(MacAlgorithm.HS256)
        .build();

    // 有効期限などの時刻と、発行者を検証する
    OAuth2TokenValidator<Jwt> issuerAndTimeValidator = JwtValidators.createDefaultWithIssuer(issuer);

    // 利用先が設定値と一致するか検証する
    OAuth2TokenValidator<Jwt> audienceValidator = new JwtClaimValidator<List<String>>(
        "aud",
        audiences -> audiences != null
            && audiences.contains(audience));

    // 両方の検証を適用する
    decoder.setJwtValidator(
        new DelegatingOAuth2TokenValidator<>(
            issuerAndTimeValidator,
            audienceValidator));

    return decoder;
  }
}