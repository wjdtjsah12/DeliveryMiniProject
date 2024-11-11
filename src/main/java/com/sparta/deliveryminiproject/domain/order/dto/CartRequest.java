package com.sparta.deliveryminiproject.domain.order.dto;

import com.sparta.deliveryminiproject.domain.order.entity.Cart;
import com.sparta.deliveryminiproject.domain.shop.entity.Menu;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartRequest {

  private UUID menuId;
  private int quantity;

  @Builder
  private CartRequest(UUID menuId, int quantity) {
    this.menuId = menuId;
    this.quantity = quantity;
  }

  public Cart toEntity(User user, Menu menu) {
    return Cart.builder()
        .user(user)
        .menu(menu)
        .shop(menu.getShop())
        .quantity(quantity)
        .build();
  }
}
