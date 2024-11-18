package com.sparta.deliveryminiproject.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SigninResponseDto {

  private Long id;

  public SigninResponseDto(Long id) {
    this.id = id;
  }
}
