package com.sparta.outsourcing.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomJwtException extends RuntimeException{
  private final HttpStatus status;
  private final String message;

  public CustomJwtException(JwtError error) {
    this.status = HttpStatus.UNAUTHORIZED;
    this.message = error.getMessage();
  }

  @Override
  public String getMessage() {
    return String.format("[JWT ERROR] %s", message);
  }
}
