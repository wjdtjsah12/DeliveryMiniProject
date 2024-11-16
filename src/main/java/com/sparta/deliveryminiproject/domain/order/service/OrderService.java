package com.sparta.deliveryminiproject.domain.order.service;

import com.sparta.deliveryminiproject.domain.cart.repository.CartRepository;
import com.sparta.deliveryminiproject.domain.order.dto.OrderRequestDto;
import com.sparta.deliveryminiproject.domain.order.dto.OrderResponseDto;
import com.sparta.deliveryminiproject.domain.order.dto.OrderSearchCondition;
import com.sparta.deliveryminiproject.domain.order.entity.Order;
import com.sparta.deliveryminiproject.domain.order.entity.OrderType;
import com.sparta.deliveryminiproject.domain.order.repository.OrderRepository;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    Order order = orderRequestDto.toEntity(user, orderRequestDto);

    cartRepository.findAllByUserAndIsDeletedFalse(user)
        .forEach(cart -> {
          order.addCartList(cart);
          cart.setIsDeleted(true);
        });

    order.setOrderType(
        user.getRole().equals(UserRoleEnum.OWNER) ? OrderType.OFFLINE : OrderType.ONLINE);

    order.calculateAndSetTotalPrice();

    return orderRepository.save(order);
  }

  public Page<OrderResponseDto> getOrders(User user, Pageable pageable, String searchQuery,
      OrderSearchCondition condition) {
    if (!isAcceptablePageSize(pageable.getPageSize())) {
      throw new ApiException("10건, 30건, 50건 기준으로만 페이지에 노출 될수 있습니다.", HttpStatus.BAD_REQUEST);
    }

    return orderRepository.getOrders(user, pageable, searchQuery, condition);
  }

  private boolean isAcceptablePageSize(int pageSize) {
    return switch (pageSize) {
      case 10, 30, 50 -> true;
      default -> false;
    };
  }
}
