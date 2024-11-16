package com.sparta.deliveryminiproject.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum OrderType {
  ONLINE("비대면 주문"),
  OFFLINE("대면 주문");

  @JsonValue
  private final String description;

  OrderType(String description) {
    this.description = description;
  }
}
