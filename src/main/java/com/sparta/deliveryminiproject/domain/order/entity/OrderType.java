package com.sparta.deliveryminiproject.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum OrderType {
  DELIVERY_ORDER("배달 주문"),
  STORE_ORDER("가게 주문");

  @JsonValue
  private final String description;

  OrderType(String description) {
    this.description = description;
  }
}
