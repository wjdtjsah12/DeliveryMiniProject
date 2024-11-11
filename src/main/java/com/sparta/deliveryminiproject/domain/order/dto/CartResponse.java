package com.sparta.deliveryminiproject.domain.order.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartResponse {

  private String shopName;
  private List<CartMenuResponse> menuList;
  private boolean availableToOrder;
  private int totalMenuPrice;
  private int deliveryTip;
  private int totalPrice;


  @Builder
  public CartResponse(String shopName, List<CartMenuResponse> menuList, boolean availableToOrder,
      int totalMenuPrice, int deliveryTip, int totalPrice) {
    this.shopName = shopName;
    this.menuList = menuList;
    this.availableToOrder = availableToOrder;
    this.totalMenuPrice = totalMenuPrice;
    this.deliveryTip = deliveryTip;
    this.totalPrice = totalPrice;
  }
}
