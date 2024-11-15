package com.sparta.deliveryminiproject.domain.order.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
  PENDING_PAYMENT("결제 대기"),
  PAID("결제 완료"),
  PREPARING("준비 중"),
  DELIVERED("배달 완료"),
  CANCELED("주문 취소"),
  REFUNDED("환불 완료");

  private final String description;

  OrderStatus(String description) {
    this.description = description;
  }
}
