package com.sparta.deliveryminiproject.domain.order.service;

import com.sparta.deliveryminiproject.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

}
