package com.sparta.deliveryminiproject.domain.order.dto;

import com.sparta.deliveryminiproject.domain.order.entity.Cart;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartMenuResponse {

  private UUID id;
  private String menuName;
  private int quantity;
  private int price;

  @Builder
  public CartMenuResponse(UUID id, String menuName, int quantity, int price) {
    this.id = id;
    this.menuName = menuName;
    this.quantity = quantity;
    this.price = price;
  }

  public static CartMenuResponse from(Cart cart) {
    return CartMenuResponse.builder()
        .id(cart.getId())
        .menuName(cart.getMenu().getMenuName())
        .quantity(cart.getQuantity())
        .price(cart.getMenu().getPrice() * cart.getQuantity())
        .build();
  }

}
