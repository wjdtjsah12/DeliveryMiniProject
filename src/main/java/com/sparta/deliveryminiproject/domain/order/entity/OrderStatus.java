package com.sparta.deliveryminiproject.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum OrderStatus {
  PENDING_PAYMENT("결제 대기"),
  PAID("결제 완료"),
  PREPARING("준비 중"),
  COMPLETED("주문 완료"),
  CANCELED("주문 취소");

  @JsonValue
  private final String description;

  OrderStatus(String description) {
    this.description = description;
  }
}
