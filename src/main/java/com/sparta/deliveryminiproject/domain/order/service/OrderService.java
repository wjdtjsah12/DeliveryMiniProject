package com.sparta.deliveryminiproject.domain.order.service;

import com.sparta.deliveryminiproject.domain.cart.repository.CartRepository;
import com.sparta.deliveryminiproject.domain.order.dto.OrderRequestDto;
import com.sparta.deliveryminiproject.domain.order.entity.Order;
import com.sparta.deliveryminiproject.domain.order.entity.OrderType;
import com.sparta.deliveryminiproject.domain.order.repository.OrderRepository;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

  private final OrderRepository orderRepository;
  private final CartRepository cartRepository;

  @Transactional
  public Order createOrder(User user, OrderRequestDto orderRequestDto) {
    Order order = orderRequestDto.toEntity(orderRequestDto);

    cartRepository.findAllByUserAndIsDeletedFalse(user)
        .forEach(order::addCartList);

    order.setOrderType(
        user.getRole().equals(UserRoleEnum.OWNER) ? OrderType.OFFLINE : OrderType.ONLINE);

    order.calculateAndSetTotalPrice();

    return orderRepository.save(order);
  }
}
