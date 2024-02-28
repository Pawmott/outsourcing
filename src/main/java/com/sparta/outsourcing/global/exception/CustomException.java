package com.sparta.outsourcing.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
  private final String message;

  public CustomException(JwtError error) {
    this.message = error.getMessage();
  }

  @Override
  public String getMessage() {
    return String.format("[CUSTOM ERROR] %s", message);
  }
}
