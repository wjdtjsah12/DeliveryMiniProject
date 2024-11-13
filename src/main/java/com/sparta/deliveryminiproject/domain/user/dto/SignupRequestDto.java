package com.sparta.deliveryminiproject.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

  @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "영소문자와 숫자가 포함된 4자~10자 사이 문자만 입력가능합니다.")
  private String username;
  @Pattern(regexp = "^[a-zA-Z0-9!@#\\$%\\^&\\*]{8,15}$", message = "영대소문자와 숫자, 특수문자가 포함된 8자~15자 사이 문자만 입력가능합니다.")
  private String password;
}
