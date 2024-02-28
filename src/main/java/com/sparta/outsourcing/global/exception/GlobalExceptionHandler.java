package com.sparta.outsourcing.global.exception;

import com.sparta.outsourcing.global.dto.CommonResponseDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomJwtException.class)
  public ResponseEntity<CommonResponseDto<String>> jwtExceptionHandler(Exception e) {
    return CommonResponseDto.unauthorizedRequest(e.getMessage());
  }

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<CommonResponseDto<String>> customExceptionHandler(Exception e) {
    return CommonResponseDto.badRequest(e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CommonResponseDto<List<String>>> methodArgumentNotValidException(
      MethodArgumentNotValidException e
  ) {
    return CommonResponseDto.of(
        HttpStatus.BAD_REQUEST,
       "잘못 입력하셨습니다.",
        null
    );
  }
}
