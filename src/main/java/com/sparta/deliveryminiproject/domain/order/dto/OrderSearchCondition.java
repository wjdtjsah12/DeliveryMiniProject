package com.sparta.deliveryminiproject.domain.order.dto;

import com.sparta.deliveryminiproject.domain.order.entity.OrderStatus;
import com.sparta.deliveryminiproject.domain.order.entity.OrderType;
import java.time.LocalDate;
import lombok.Data;

@Data
public class OrderSearchCondition {

  private OrderType orderType;
  private LocalDate startAt;
  private LocalDate endAt;
  private OrderStatus orderStatus;

}
