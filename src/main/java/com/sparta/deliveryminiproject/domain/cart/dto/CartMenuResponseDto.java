package com.sparta.deliveryminiproject.domain.cart.dto;

import com.sparta.deliveryminiproject.domain.cart.entity.Cart;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CartMenuResponseDto {

  private UUID id;
  private String menuName;
  private int quantity;
  private int price;

  @Builder
  public CartMenuResponseDto(UUID id, String menuName, int quantity, int price) {
    this.id = id;
    this.menuName = menuName;
    this.quantity = quantity;
    this.price = price;
  }

  public static CartMenuResponseDto from(Cart cart) {
    return CartMenuResponseDto.builder()
        .id(cart.getId())
        .menuName(cart.getMenu().getMenuName())
        .quantity(cart.getQuantity())
        .price(cart.getMenu().getPrice() * cart.getQuantity())
        .build();
  }

}
