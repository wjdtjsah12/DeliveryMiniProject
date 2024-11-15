package com.sparta.deliveryminiproject.domain.review.entity;

import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import com.sparta.deliveryminiproject.domain.review.dto.ReviewRequestDto;
import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import com.sparta.deliveryminiproject.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_review")
public class Review extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private Integer rating;

  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "shop_id", nullable = false)
  private Shop shop;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_id", nullable = false)
  private Menu menu;

  public Review(Shop shop, Menu menu, ReviewRequestDto reviewRequestDto) {
    this.shop = shop;
    this.menu = menu;
    this.rating = reviewRequestDto.getRating();
    if (reviewRequestDto.getContent() != null) {
      this.content = reviewRequestDto.getContent();
    }
  }

  public void update(ReviewRequestDto reviewRequestDto) {
    this.rating = reviewRequestDto.getRating();
    this.content = reviewRequestDto.getContent();
  }
}
