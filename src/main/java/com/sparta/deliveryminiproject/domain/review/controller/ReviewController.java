package com.sparta.deliveryminiproject.domain.review.controller;

import com.sparta.deliveryminiproject.domain.review.dto.ReviewRequestDto;
import com.sparta.deliveryminiproject.domain.review.dto.ReviewResponseDto;
import com.sparta.deliveryminiproject.domain.review.service.ReviewService;
import com.sparta.deliveryminiproject.global.security.UserDetailsImpl;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("/{reviewId}")
  public ResponseEntity getReview(
      @PathVariable UUID shopId,
      @PathVariable UUID reviewId) {

    ReviewResponseDto reviewResponseDto = reviewService.getReview(shopId, reviewId);

    return ResponseEntity.ok(reviewResponseDto);
  }

  @GetMapping
  public ResponseEntity getReviewList(
      @PathVariable UUID shopId,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "DESC") Direction direction,
      Pageable pageable) {

    Page<ReviewResponseDto> pagedReviewDtoList = reviewService.getReviewList(
        shopId, size, sortBy, direction, pageable);

    return ResponseEntity.ok(pagedReviewDtoList);
  }

  @PutMapping("/{reviewId}")
  public ResponseEntity updateReview(
      @PathVariable UUID shopId,
      @PathVariable UUID reviewId,
      @ModelAttribute ReviewRequestDto reviewRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    ReviewResponseDto reviewResponseDto = reviewService.updateReview(shopId, reviewId,
        reviewRequestDto, userDetails.getUser());

    return ResponseEntity.ok(reviewResponseDto);
  }
}
