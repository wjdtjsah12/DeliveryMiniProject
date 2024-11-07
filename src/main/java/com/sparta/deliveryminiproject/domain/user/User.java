package com.sparta.deliveryminiproject.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "p_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private Long id;

  private String username;

  private String password;

  private String role;
}
