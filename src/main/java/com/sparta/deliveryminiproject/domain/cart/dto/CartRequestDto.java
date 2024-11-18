package com.sparta.deliveryminiproject.domain.cart.dto;

import com.sparta.deliveryminiproject.domain.cart.entity.Cart;
import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartRequestDto {

  @NotNull
  private UUID menuId;
  @Positive
  private int quantity;

  @Builder
  private CartRequestDto(UUID menuId, int quantity) {
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
