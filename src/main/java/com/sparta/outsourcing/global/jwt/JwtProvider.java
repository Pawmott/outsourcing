package com.sparta.outsourcing.global.jwt;

import static com.sparta.outsourcing.global.jwt.TokenState.EXPIRED;
import static com.sparta.outsourcing.global.jwt.TokenState.INVALID;
import static com.sparta.outsourcing.global.jwt.TokenState.VALID;

import com.sparta.outsourcing.domain.member.model.MemberRole;
import com.sparta.outsourcing.global.exception.CustomJwtException;
import com.sparta.outsourcing.global.exception.JwtError;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "JWT 토큰")
@Component
public class JwtProvider {

  public static final String AUTHORIZATION_ACCESS_TOKEN_HEADER_KEY = "Authorization";
  private static final String AUTHORIZATION_KEY = "Auth";
  private static final String BEARER_PREFIX = "Bearer ";
  private static final Integer BEARER_PREFIX_LENGTH = 7;
  private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
  private static final Long ACCESS_TOKEN_VALID_TIME = (60 * 1000L) * 30;
  private static final Long REFRESH_TOKEN_VALID_TIME = (60 * 1000L) * 60 * 24 * 7;

  @Value("${jwt.secret.key}")
  private String secretKey;
  private Key key;

  @PostConstruct
  public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);
  }

  public String generateAccessToken(final String name, final MemberRole role) {
    return generateToken(name, role, ACCESS_TOKEN_VALID_TIME);
  }

  public String generateRefreshToken(final String name, final MemberRole role) {
    return generateRefreshToken(name, role, REFRESH_TOKEN_VALID_TIME);
  }

  public String generateToken(final String info, final MemberRole role, Long time) {
    Date now = new Date();
    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(info)
            .claim(AUTHORIZATION_KEY, role)
            .setExpiration(new Date(now.getTime() + time))
            .setIssuedAt(now)
            .signWith(key, SIGNATURE_ALGORITHM)
            .compact();
  }

  public String generateRefreshToken(final String info, final MemberRole role, Long time) {
    Date now = new Date();
    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(info)
            .claim(AUTHORIZATION_KEY, role)
            .setExpiration(new Date(now.getTime() + time))
            .setIssuedAt(now)
            .signWith(key, SIGNATURE_ALGORITHM)
            .compact();
  }

  public String getAccessTokenFromRequest(final HttpServletRequest request) {
    return getTokenFromRequest(request);
  }

  public String substringToken(final String tokenValue) {
    if (!StringUtils.hasText(tokenValue) || !tokenValue.startsWith(BEARER_PREFIX)) {
      throw new CustomJwtException(JwtError.INVALID_TOKEN);
    }

    return tokenValue.substring(BEARER_PREFIX_LENGTH);
  }

  // Request 안에 토큰을 가져와서 복호화 하는 메서드
  private String getTokenFromRequest(
      final HttpServletRequest request
  ) {
    String bearerToken = request.getHeader(AUTHORIZATION_ACCESS_TOKEN_HEADER_KEY);

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(BEARER_PREFIX_LENGTH);    // 순수한 jwt 코드를 뽑아옴
    }

    return null;
  }

  // 토큰 정보를 검증하는 메서드
  public TokenState validateToken(final String token) {
    if (!StringUtils.hasText(token)) {
      return INVALID;
    }

    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return VALID;
    } catch (SecurityException | MalformedJwtException |
             SignatureException e) {
      log.error("[Invalid JWT signature]", e);
      return INVALID;
    } catch (ExpiredJwtException e) {
      log.error("[Expired JWT token]", e);
      return EXPIRED;
    } catch (UnsupportedJwtException e) {
      log.error("[Unsupported JWT token]", e);
      return INVALID;
    } catch (IllegalArgumentException e) {
      log.error("[JWT claims is empty]", e);
      return INVALID;
    }
  }

  public Claims getMemberInfoFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

  // 만료된 토큰으로부터 정보를 가져오기
  public Claims getMemberInfoFromExpiredToken(String token) {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
