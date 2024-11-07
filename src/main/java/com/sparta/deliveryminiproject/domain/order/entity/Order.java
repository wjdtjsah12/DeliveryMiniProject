package com.sparta.deliveryminiproject.domain.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "p_order")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private Long id;

  private int totalPrice;

  private String requests;

  private String address;

}
