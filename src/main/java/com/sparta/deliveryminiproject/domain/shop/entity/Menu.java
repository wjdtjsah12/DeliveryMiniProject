package com.sparta.deliveryminiproject.domain.shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_menu")
@Getter
@NoArgsConstructor
public class Menu {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String menuName;

  private String description;

  private int price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "shop_id")
  private Shop shop;

  @Builder
  private Menu(UUID id, String menuName, String description, int price, Shop shop) {
    this.id = id;
    this.menuName = menuName;
    this.description = description;
    this.price = price;
    this.shop = shop;
  }
}
