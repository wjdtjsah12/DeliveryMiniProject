package com.sparta.deliveryminiproject.domain.order.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sparta.deliveryminiproject.domain.order.entity.OrderStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class OrderResponseDto {

  private UUID orderId;
  private LocalDate orderedAt;
  private OrderStatus orderStatus;
  private String shopName;
  private int totalPrice;

  @QueryProjection
  public OrderResponseDto(UUID orderId, LocalDateTime orderedAt, OrderStatus orderStatus,
      String shopName,
      int totalPrice) {
    this.orderId = orderId;
    this.orderedAt = LocalDate.from(orderedAt);
    this.orderStatus = orderStatus;
    this.shopName = shopName;
    this.totalPrice = totalPrice;
  }
}
