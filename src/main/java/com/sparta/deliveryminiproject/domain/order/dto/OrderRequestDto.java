package com.sparta.deliveryminiproject.domain.order.dto;

import com.sparta.deliveryminiproject.domain.order.entity.Order;
import com.sparta.deliveryminiproject.domain.order.entity.OrderStatus;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderRequestDto {

  private String requests;
  private String address;
  private String phoneNumber;

  @Builder
  private OrderRequestDto(String requests, String address, String phoneNumber) {
    this.requests = requests;
    this.address = address;
    this.phoneNumber = phoneNumber;
  }

  public Order toEntity(User user, OrderRequestDto orderRequestDto) {
    return Order.builder()
        .requests(orderRequestDto.getRequests())
        .address(orderRequestDto.getAddress())
        .phoneNumber(orderRequestDto.getPhoneNumber())
        .orderStatus(OrderStatus.PENDING_PAYMENT)
        .user(user)
        .build();
  }
}
