package com.sparta.deliveryminiproject.domain.cart.dto;

import com.sparta.deliveryminiproject.domain.cart.entity.Cart;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartInfo {

  private UUID cartId;
  private String menuName;
  private int quantity;
  private int price;

  @Builder
  public CartInfo(UUID cartId, String menuName, int quantity, int price) {
    this.cartId = cartId;
    this.menuName = menuName;
    this.quantity = quantity;
    this.price = price;
  }

  public static CartInfo from(Cart cart) {
    return CartInfo.builder()
        .cartId(cart.getId())
        .menuName(cart.getMenu().getMenuName())
        .quantity(cart.getQuantity())
        .price(cart.getMenu().getPrice() * cart.getQuantity())
        .build();
  }

}
