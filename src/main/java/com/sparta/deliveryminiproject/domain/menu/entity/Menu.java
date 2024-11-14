package com.sparta.deliveryminiproject.domain.menu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "p_menu")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Menu {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

}
