package com.sparta.deliveryminiproject.domain.order.dto;

import com.sparta.deliveryminiproject.domain.order.entity.Cart;
import com.sparta.deliveryminiproject.domain.shop.entity.Menu;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartRequestDto {

  @NotBlank
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
