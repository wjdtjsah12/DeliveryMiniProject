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
import com.sparta.deliveryminiproject.global.exception.ApiException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
}
