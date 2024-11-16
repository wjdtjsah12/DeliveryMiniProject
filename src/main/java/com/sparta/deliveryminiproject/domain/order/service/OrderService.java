package com.sparta.deliveryminiproject.domain.order.service;

import com.sparta.deliveryminiproject.domain.cart.repository.CartRepository;
import com.sparta.deliveryminiproject.domain.order.dto.MenuInfo;
import com.sparta.deliveryminiproject.domain.order.dto.OrderDetailsResponseDto;
import com.sparta.deliveryminiproject.domain.order.dto.OrderRequestDto;
import com.sparta.deliveryminiproject.domain.order.dto.OrderResponseDto;
import com.sparta.deliveryminiproject.domain.order.dto.OrderSearchCondition;
import com.sparta.deliveryminiproject.domain.order.entity.Order;
import com.sparta.deliveryminiproject.domain.order.entity.OrderStatus;
import com.sparta.deliveryminiproject.domain.order.entity.OrderType;
import com.sparta.deliveryminiproject.domain.order.repository.OrderRepository;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
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

    order.setShop(order.getCartList().get(0).getShop());

    order.setOrderType(
        user.getRole().equals(UserRoleEnum.OWNER) ? OrderType.STORE_ORDER
            : OrderType.DELIVERY_ORDER);

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

  public OrderDetailsResponseDto getOrderDetails(User user, UUID orderId) {
    Order order = orderRepository.findByIdAndUserAndIsDeletedFalse(orderId, user)
        .orElseThrow(() -> new ApiException("주문 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

    List<MenuInfo> menuList = order.getCartList().stream()
        .map(MenuInfo::from)
        .toList();

    int menuPrice = menuList.stream()
        .mapToInt(MenuInfo::getPrice)
        .sum();

    return OrderDetailsResponseDto.builder()
        .shopName(order.getShop().getShopName())
        .orderStatus(order.getOrderStatus())
        .orderType(order.getOrderType())
        .menuList(menuList)
        .menuPrice(menuPrice)
        .deliveryTip(order.getShop().getDeliveryTip())
        .totalPrice(order.getTotalPrice())
        .paymentMethod(order.getPayment().getPaymentMethod())
        .build();
  }

  @Transactional
  public void updateOrderStatus(User user, UUID orderId, OrderStatus orderStatus) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new ApiException("주문 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

    if (orderStatus.equals(OrderStatus.CANCELED)) {
      if (!order.getUser().getId().equals(user.getId())) {
        throw new ApiException("해당 주문의 상태를 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN);
      }

      LocalDateTime createdAt = order.getCreatedAt();
      LocalDateTime now = LocalDateTime.now();

      if (createdAt.plusMinutes(5).isBefore(now)) {
        throw new ApiException("주문 후 5분이 지나 취소할 수 없습니다.", HttpStatus.BAD_REQUEST);
      }
    } else {
      if (!order.getShop().getUser().getId().equals(user.getId())) {
        throw new ApiException("해당 주문의 상태를 수정할 권한이 없습니다.", HttpStatus.FORBIDDEN);
      }
    }

    order.setOrderStatus(orderStatus);
  }

  @Transactional
  public void deleteOrder(User user, UUID orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new ApiException("주문 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

    if (!order.getUser().getId().equals(user.getId())) {
      throw new ApiException("해당 주문을 삭제할 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }

    order.setIsDeleted(true);
  }
}
