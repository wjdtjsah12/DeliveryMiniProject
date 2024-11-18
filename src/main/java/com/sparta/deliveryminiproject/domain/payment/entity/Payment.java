package com.sparta.deliveryminiproject.domain.payment.entity;

import com.sparta.deliveryminiproject.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

  @Builder
  private Payment(UUID id, String paymentMethod, int totalPrice) {
    this.id = id;
    this.paymentMethod = paymentMethod;
    this.totalPrice = totalPrice;
  }
}
