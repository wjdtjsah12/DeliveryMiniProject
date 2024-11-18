package com.sparta.deliveryminiproject.domain.order.dto;

import com.sparta.deliveryminiproject.domain.order.entity.OrderStatus;
import com.sparta.deliveryminiproject.domain.order.entity.OrderType;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class OrderDetailsResponseDto {

  private String shopName;
  private OrderStatus orderStatus;
  private OrderType orderType;
  private List<MenuInfo> menuList;
  private int menuPrice;
  private int deliveryTip;
  private int totalPrice;
  private String paymentMethod;

  @Builder
  private OrderDetailsResponseDto(String shopName, OrderStatus orderStatus, OrderType orderType,
      List<MenuInfo> menuList, int menuPrice, int deliveryTip, int totalPrice,
      String paymentMethod) {
    this.shopName = shopName;
    this.orderStatus = orderStatus;
    this.orderType = orderType;
    this.menuList = menuList;
    this.menuPrice = menuPrice;
    this.deliveryTip = deliveryTip;
    this.totalPrice = totalPrice;
    this.paymentMethod = paymentMethod;
  }
}
