package com.sparta.deliveryminiproject.domain.payment.entity;

import com.sparta.deliveryminiproject.domain.order.entity.Order;
import com.sparta.deliveryminiproject.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String paymentMethod;

  private int totalPrice;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  @Builder
  private Payment(UUID id, String paymentMethod, int totalPrice, Order order) {
    this.id = id;
    this.paymentMethod = paymentMethod;
    this.totalPrice = totalPrice;
    this.order = order;
  }
}
