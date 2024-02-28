package com.sparta.outsourcing.domain.member.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSignupDto {

  private final String email;
  private final String nickname;
  private final String password;
  private final String address;
  private final String number;
}
