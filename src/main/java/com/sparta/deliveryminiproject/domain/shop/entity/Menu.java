package com.sparta.deliveryminiproject.domain.shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "p_menu")
public class Menu {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private Long id;

  private String menuName;

  private String description;

  private String price;

}
