package com.sparta.outsourcing.domain.member.service.dto;

import com.sparta.outsourcing.domain.member.controller.dto.UpdateRequestDto;
import lombok.Getter;

@Getter
public class UpdateDto {
  private final String nickname;
  private final String password;
  private final String address;
  private final String number;

  public UpdateDto (UpdateRequestDto dto) {
    nickname = dto.getNickname();
    password = dto.getPassword();
    address = dto.getAddress();
    number = dto.getNumber();
  }
}
