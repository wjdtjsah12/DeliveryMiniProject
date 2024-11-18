package com.sparta.deliveryminiproject.domain.user.dto;

import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleResponseDto {

  private Long id;
  UserRoleEnum role;

  public RoleResponseDto(Long id, UserRoleEnum role) {
    this.id = id;
    this.role = role;
  }

}
