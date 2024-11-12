package com.sparta.deliveryminiproject.domain.shop.service;

import com.sparta.deliveryminiproject.domain.shop.dto.ShopRequestDto;
import com.sparta.deliveryminiproject.domain.shop.dto.ShopResponseDto;
import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import com.sparta.deliveryminiproject.domain.shop.repository.ShopRepository;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import com.sparta.deliveryminiproject.domain.user.repository.UserRepository;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {

  private final ShopRepository shopRepository;
  private final UserRepository userRepository;

  public ShopResponseDto createShop(ShopRequestDto shopRequestDto) {

    if (shopRepository.findShopByShopName(shopRequestDto.getShopName()).isPresent()) {
      throw new ApiException("중복된 가게 이름 입니다.", HttpStatus.BAD_REQUEST);
    }

    User owner = userRepository.findById(shopRequestDto.getUserId())
        .orElseThrow(() -> new ApiException("해당 사용자가 없습니다.", HttpStatus.NOT_FOUND));

    Shop shop = new Shop(shopRequestDto, owner);

    shopRepository.save(shop);

    return new ShopResponseDto(shop);
  }

  public ShopResponseDto getShop(Long shopId) {

    return new ShopResponseDto(shopRepository.findById(shopId)
        .orElseThrow(() -> new ApiException("존재하지 않는 가게입니다.", HttpStatus.NOT_FOUND)));
  }


}
