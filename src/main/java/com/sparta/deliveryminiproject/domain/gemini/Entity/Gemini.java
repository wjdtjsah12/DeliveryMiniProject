package com.sparta.deliveryminiproject.domain.gemini.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity(name = "p_gemini")
@NoArgsConstructor
public class Gemini {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "gemini_id")
  private UUID id;

  @Setter
  @Column(nullable = false)
  private String requestKeyword;

}
