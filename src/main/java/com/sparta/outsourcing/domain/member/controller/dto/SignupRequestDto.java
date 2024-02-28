package com.sparta.outsourcing.domain.member.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

  @NotBlank
  @Email
  @Size(max = 40)
  private String email;

  @NotBlank
  @Size(max = 20)
  private String nickname;

  @NotBlank
  @Size(min = 8, max = 15)
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&*!])[A-Za-z\\d@#$%^&*!]+$")
  private String password;

  @NotBlank
  private String address;

  @NotBlank
  @Pattern(regexp = "^01(0|1|6|7|8|9)-\\d{3,4}-\\d{4}$")
  private String number;
}
