package com.sparta.deliveryminiproject.domain.order.controller;

import com.sparta.deliveryminiproject.domain.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;
}
