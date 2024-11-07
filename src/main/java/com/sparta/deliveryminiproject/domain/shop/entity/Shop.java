package com.sparta.deliveryminiproject.domain.shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "p_shop")
public class Shop {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private Long id;

  private String shopName;

  private String description;

  private int minDeliveryPrice;

  private int deliveryTip;

}
