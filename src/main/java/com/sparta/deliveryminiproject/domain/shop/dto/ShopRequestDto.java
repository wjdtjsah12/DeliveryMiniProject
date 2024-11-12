package com.sparta.deliveryminiproject.domain.shop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopRequestDto {

  private String shopName;

  private Long userId;

  private String address;

  private String description;

  private Integer minDeliveryPrice;

  private Integer deliveryTip;

  private Boolean isHidden = false;
}
