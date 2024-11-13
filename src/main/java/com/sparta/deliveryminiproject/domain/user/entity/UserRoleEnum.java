package com.sparta.deliveryminiproject.domain.user.entity;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
  CUSTOMER(Authority.CUSTOMER),
  OWNER(Authority.OWNER),
  MANAGER(Authority.MANAGER),
  MASTER(Authority.MASTER);

  private final String authority;

  UserRoleEnum(String authority) {
    this.authority = authority;
  }

  public static UserRoleEnum contains(String role) {
    for (UserRoleEnum userRole : UserRoleEnum.values()) {
      if (userRole.getAuthority().equals(role)) {
        return userRole;
      }
    }
    return null;
  }

  public static class Authority {

    public static final String CUSTOMER = "ROLE_CUSTOMER";
    public static final String OWNER = "ROLE_OWNER";
    public static final String MANAGER = "ROLE_MANAGER";
    public static final String MASTER = "ROLE_MASTER";
  }
}
