package com.sparta.deliveryminiproject.domain.cart.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartResponseDto {

  private String shopName;
  private List<CartInfo> menuList;
  private boolean availableToOrder;
  private int totalMenuPrice;
  private int deliveryTip;
  private int totalPrice;


  @Builder
  public CartResponseDto(String shopName, List<CartInfo> menuList,
      boolean availableToOrder,
      int totalMenuPrice, int deliveryTip, int totalPrice) {
    this.shopName = shopName;
    this.menuList = menuList;
    this.availableToOrder = availableToOrder;
    this.totalMenuPrice = totalMenuPrice;
    this.deliveryTip = deliveryTip;
    this.totalPrice = totalPrice;
  }
}
