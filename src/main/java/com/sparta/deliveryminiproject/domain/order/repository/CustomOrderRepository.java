package com.sparta.deliveryminiproject.domain.order.repository;

import com.sparta.deliveryminiproject.domain.order.dto.OrderResponseDto;
import com.sparta.deliveryminiproject.domain.order.dto.OrderSearchCondition;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomOrderRepository {

  Page<OrderResponseDto> getOrders(User user, Pageable pageable, String searchQuery,
      OrderSearchCondition condition);
}
