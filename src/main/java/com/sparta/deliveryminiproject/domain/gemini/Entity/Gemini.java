package com.sparta.deliveryminiproject.domain.gemini.Entity;

import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  @ManyToOne
  @JoinColumn(name = "menu_id")
  private Menu menu;

  @Setter
  @Column(nullable = false)
  private String geminiDescription;

  public Gemini(Menu menu, String geminiDescription) {
    this.menu = menu;
    this.geminiDescription = geminiDescription;
  }
}
