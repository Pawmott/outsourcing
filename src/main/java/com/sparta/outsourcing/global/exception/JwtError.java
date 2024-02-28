package com.sparta.outsourcing.global.exception;

import lombok.Getter;

@Getter
public enum JwtError {
  WRONG_JWT_SIGNATURE("잘못된 JWT 입니다."),
  EXPIRED_JWT("JWT 토큰이 만료되었습니다."),
  UNSUPPORTED_TOKEN_TYPE("JWT 토큰 유형이 잘못되었습니다."),
  INVALID_TOKEN("JWT 토큰이 잘못되었습니다.");

  private final String message;

  JwtError(String message) {
    this.message = message;
  }
}
