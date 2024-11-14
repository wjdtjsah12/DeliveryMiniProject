package com.sparta.deliveryminiproject.domain.order.entity;

import lombok.Getter;

@Getter
public enum OrderType {
  ONLINE("온라인 주문"),
  STORE("대면 주문");

  private final String description;

  OrderType(String description) {
    this.description = description;
  }
}
