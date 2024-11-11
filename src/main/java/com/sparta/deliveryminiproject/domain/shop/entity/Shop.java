package com.sparta.deliveryminiproject.domain.shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_shop")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Shop {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String shopName;

  private String description;

  private int minDeliveryPrice;

  private int deliveryTip;

  public Shop(String shopName, String description, int minDeliveryPrice, int deliveryTip) {
    this.shopName = shopName;
    this.description = description;
    this.minDeliveryPrice = minDeliveryPrice;
    this.deliveryTip = deliveryTip;
  }
}
