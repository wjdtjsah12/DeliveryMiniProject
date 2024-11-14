package com.sparta.deliveryminiproject.domain.review.controller;

import com.sparta.deliveryminiproject.domain.review.dto.ReviewRequestDto;
import com.sparta.deliveryminiproject.domain.review.dto.ReviewResponseDto;
import com.sparta.deliveryminiproject.domain.review.service.ReviewService;
import com.sparta.deliveryminiproject.global.security.UserDetailsImpl;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shops/{shopId}/reviews")
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping
  public ResponseEntity createReview(
      @PathVariable UUID shopId,
      @ModelAttribute ReviewRequestDto reviewRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    ReviewResponseDto reviewResponseDto = reviewService.createReview(shopId, reviewRequestDto,
        userDetails.getUser());

    return ResponseEntity.ok(reviewResponseDto);
  }
}
