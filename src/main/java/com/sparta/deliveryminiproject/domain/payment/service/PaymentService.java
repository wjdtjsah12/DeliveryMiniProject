package com.sparta.deliveryminiproject.domain.payment.service;

import com.sparta.deliveryminiproject.domain.order.entity.Order;
import com.sparta.deliveryminiproject.domain.order.entity.OrderStatus;
import com.sparta.deliveryminiproject.domain.payment.entity.Payment;
import com.sparta.deliveryminiproject.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

  private final PaymentRepository paymentRepository;

  @Transactional
  public void payToOrder(Order order) {
    Payment payment = Payment.builder()
        .paymentMethod("카드 결제")
        .totalPrice(order.getTotalPrice())
        .build();

    order.setPayment(payment);
    order.setOrderStatus(OrderStatus.PAID);

    paymentRepository.save(payment);
  }
}
