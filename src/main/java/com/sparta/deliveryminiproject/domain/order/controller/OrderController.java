package com.sparta.deliveryminiproject.domain.order.controller;

import com.sparta.deliveryminiproject.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

}
