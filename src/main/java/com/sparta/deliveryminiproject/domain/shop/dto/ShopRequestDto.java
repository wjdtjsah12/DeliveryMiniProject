package com.sparta.deliveryminiproject.domain.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopRequestDto {

  private String shopName;

  private Long userId;

  private String address;

}
