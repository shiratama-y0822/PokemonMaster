package com.shiratama.pokemonmaster.login;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final JwtEncoder jwtEncoder;
  private final Duration ttl;
  private final String issuer;
  private final String audience;

  public JwtService(
      JwtEncoder jwtEncoder,
      @Value("${app.jwt.ttl}") String ttl,
      @Value("${app.jwt.issuer}") String issuer,
      @Value("${app.jwt.audience}") String audience) {

    this.jwtEncoder = jwtEncoder;
    this.ttl = DurationStyle.detectAndParse(ttl);
    this.issuer = issuer;
    this.audience = audience;

    if (this.ttl.isZero() || this.ttl.isNegative()) {
      throw new IllegalArgumentException(
          "JWTの有効期間は0より大きい値にしてください");
    }
  }

  public String issueToken(String userId) {

    Instant now = Instant.now();

    // JWTに含める情報を組み立てる
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .subject(userId)
        .issuedAt(now)
        .expiresAt(now.plus(ttl))
        .issuer(issuer)
        .audience(List.of(audience))
        .build();

    // 署名方式を指定する
    JwsHeader header = JwsHeader.with(MacAlgorithm.HS256)
        .build();

    // 署名付きJWTを発行し、文字列として返す
    return jwtEncoder.encode(
        JwtEncoderParameters.from(header, claims))
        .getTokenValue();
  }
}