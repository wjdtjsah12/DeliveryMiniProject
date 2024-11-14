package com.sparta.deliveryminiproject.domain.review.dto;

import com.sparta.deliveryminiproject.domain.review.entity.Review;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {

  private UUID shopId;

  private UUID menuId;

  private UUID reviewId;

  private Integer rating;

  private String content;

  private String username;

  public ReviewResponseDto(Review review) {
    this.shopId = review.getShop().getId();
    this.menuId = review.getMenu().getId();
    this.reviewId = review.getId();
    this.rating = review.getRating();
    this.content = review.getContent();
    this.username = review.getCreatedBy();
  }
}
