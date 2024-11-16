package com.sparta.deliveryminiproject.domain.order.controller;

import com.sparta.deliveryminiproject.domain.order.dto.OrderRequestDto;
import com.sparta.deliveryminiproject.domain.order.dto.OrderSearchCondition;
import com.sparta.deliveryminiproject.domain.order.entity.Order;
import com.sparta.deliveryminiproject.domain.order.entity.OrderStatus;
import com.sparta.deliveryminiproject.domain.order.service.OrderService;
import com.sparta.deliveryminiproject.domain.payment.service.PaymentService;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum.Authority;
import com.sparta.deliveryminiproject.global.security.UserDetailsImpl;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;
  private final PaymentService paymentService;

  @PostMapping
  public ResponseEntity createOrder(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody OrderRequestDto orderRequestDto) {
    Order order = orderService.createOrder(userDetails.getUser(), orderRequestDto);
    paymentService.payToOrder(order);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity getOrders(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable,
      @RequestParam(required = false) String searchQuery,
      @RequestBody(required = false) OrderSearchCondition condition
  ) {
    return ResponseEntity.ok(
        orderService.getOrders(userDetails.getUser(), pageable, searchQuery, condition));
  }

  @GetMapping("/{orderId}")
  public ResponseEntity getOrderDetails(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable UUID orderId
  ) {
    return ResponseEntity.ok(orderService.getOrderDetails(userDetails.getUser(), orderId));
  }

  @Secured(Authority.OWNER)
  @PutMapping("/{orderId}")
  public ResponseEntity updateOrderStatus(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable UUID orderId,
      @RequestParam OrderStatus orderStatus
  ) {
    orderService.updateOrderStatus(userDetails.getUser(), orderId, orderStatus);
    return ResponseEntity.ok().build();
  }
}
