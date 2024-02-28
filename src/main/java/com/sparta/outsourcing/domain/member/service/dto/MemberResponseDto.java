package com.sparta.outsourcing.domain.member.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponseDto {

  private final String email;
  private final String nickname;

  public MemberResponseDto(String email, String nickname) {
    this.email = email;
    this.nickname = nickname;
  }
}
