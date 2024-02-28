package com.sparta.outsourcing.domain.member.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateRequestDto {
  private String nickname;
  private String password;
  private String address;
  private String number;
}
