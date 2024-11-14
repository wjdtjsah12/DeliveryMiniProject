package com.sparta.deliveryminiproject.domain.review.service;

import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import com.sparta.deliveryminiproject.domain.menu.repository.MenuRepository;
import com.sparta.deliveryminiproject.domain.review.dto.ReviewRequestDto;
import com.sparta.deliveryminiproject.domain.review.dto.ReviewResponseDto;
import com.sparta.deliveryminiproject.domain.review.entity.Review;
import com.sparta.deliveryminiproject.domain.review.repository.ReviewRepository;
import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import com.sparta.deliveryminiproject.domain.shop.repository.ShopRepository;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final ShopRepository shopRepository;
  private final MenuRepository menuRepository;

  public ReviewResponseDto createReview(UUID shopId, ReviewRequestDto reviewRequestDto, User user) {

    Shop shop = shopRepository.findShopByIdOrElseThrow(shopId);
    Menu menu = menuRepository.findMenuByIdOrElseThrow(reviewRequestDto.getMenuId());

    if (!menu.getShop().getId().equals(shop.getId())) {
      throw new ApiException("해당 가게의 메뉴가 아닙니다.", HttpStatus.BAD_REQUEST);
    }

    if (shop.getUser().getId().equals(user.getId())) {
      throw new ApiException("본인 소유의 가게입니다.", HttpStatus.BAD_REQUEST);
    }

    Review review = new Review(shop, menu, reviewRequestDto);

    reviewRepository.save(review);

    return new ReviewResponseDto(review);
  }

  public ReviewResponseDto getReview(UUID shopId, UUID reviewId) {

    shopRepository.findShopByIdOrElseThrow(shopId);
    Review review = reviewRepository.findReviewByIdOrElseThrow(reviewId);

    return new ReviewResponseDto(review);
  }

  public Page<ReviewResponseDto> getReviewList(
      UUID shopId, int size,
      String sortBy, Direction direction, Pageable pageable) {

    Sort sort = Sort.by(direction, sortBy);
    pageable = PageRequest.of(pageable.getPageNumber(), size, sort);

    // size를 10, 30, 50로 제한
    size = (size == 30 || size == 50) ? size : 10;  // size가 30이나 50이 아니면 10으로 고정

    pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());

    Shop shop = shopRepository.findShopByIdOrElseThrow(shopId);

    Page<ReviewResponseDto> pagedReivewResponseDtoList
        = reviewRepository.findByShopIdAndIsDeletedFalse(shopId, pageable)
        .map(ReviewResponseDto::new);

    return pagedReivewResponseDtoList;
  }

  @Transactional
  public ReviewResponseDto updateReview(UUID shopId, UUID reviewId,
      ReviewRequestDto reviewRequestDto, User user) {

    Shop shop = shopRepository.findShopByIdOrElseThrow(shopId);
    Review review = reviewRepository.findReviewByIdOrElseThrow(reviewId);

    if (!shop.getId().equals(review.getShop().getId())) {
      throw new ApiException("해당 가게의 리뷰가 아닙니다.", HttpStatus.BAD_REQUEST);
    }

    validateUserIsReviewAuthor(user, review);

    review.update(reviewRequestDto);

    return new ReviewResponseDto(review);
  }

  @Transactional
  public ReviewResponseDto deleteReview(UUID shopId, UUID reviewId, User user) {

    Shop shop = shopRepository.findShopByIdOrElseThrow(shopId);
    Review review = reviewRepository.findReviewByIdOrElseThrow(reviewId);

    if (!shop.getId().equals(review.getShop().getId())) {
      throw new ApiException("해당 가게의 리뷰가 아닙니다.", HttpStatus.BAD_REQUEST);
    }

    validateUserIsReviewAuthor(user, review);

    review.setIsDeleted(true);

    return new ReviewResponseDto(review);
  }


  private void validateUserIsReviewAuthor(User user, Review review) {
    if (!(user.getRole().equals(UserRoleEnum.MANAGER) ||
        user.getRole().equals(UserRoleEnum.MASTER))) {
      if (!review.getCreatedBy().equals(user.getUsername())) {
        throw new ApiException("리뷰 작성자가 아닙니다.", HttpStatus.UNAUTHORIZED);
      }
    }
  }


}
