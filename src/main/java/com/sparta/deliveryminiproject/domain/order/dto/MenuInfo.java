package com.sparta.deliveryminiproject.domain.order.dto;

import com.sparta.deliveryminiproject.domain.cart.entity.Cart;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuInfo {

  private UUID menuId;
  private String menuName;
  private int quantity;
  private int price;

  @Builder
  public MenuInfo(UUID menuId, String menuName, int quantity, int price) {
    this.menuId = menuId;
    this.menuName = menuName;
    this.quantity = quantity;
    this.price = price;
  }

  public static MenuInfo from(Cart cart) {
    return MenuInfo.builder()
        .menuId(cart.getMenu().getId())
        .menuName(cart.getMenu().getMenuName())
        .quantity(cart.getQuantity())
        .price(cart.getMenu().getPrice() * cart.getQuantity())
        .build();
  }

}
