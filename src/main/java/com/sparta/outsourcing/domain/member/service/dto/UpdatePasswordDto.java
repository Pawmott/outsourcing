package com.sparta.outsourcing.domain.member.service.dto;

import com.sparta.outsourcing.domain.member.controller.dto.UpdatePasswordRequestDto;
import lombok.Getter;

@Getter
public class UpdatePasswordDto {

  private final String changePassword;
  private final String currentPassword;
  private final String reCurrentPassword;

  public UpdatePasswordDto (UpdatePasswordRequestDto dto) {
    changePassword = dto.getChangePassword();
    currentPassword = dto.getCurrentPassword();
    reCurrentPassword = dto.getReCurrentPassword();
  }

  public void checkChangePasswordEquals() {
    if(!currentPassword.equals(reCurrentPassword)) {
      throw new IllegalArgumentException("변경할 비밀번호가 일치하지 않습니다.");
    }
  }
}
