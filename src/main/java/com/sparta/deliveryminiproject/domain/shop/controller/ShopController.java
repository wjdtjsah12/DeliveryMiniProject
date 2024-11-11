package com.sparta.deliveryminiproject.domain.shop.controller;

import com.sparta.deliveryminiproject.domain.shop.dto.ShopRequestDto;
import com.sparta.deliveryminiproject.domain.shop.dto.ShopResponseDto;
import com.sparta.deliveryminiproject.domain.shop.service.ShopService;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum.Authority;
import com.sparta.deliveryminiproject.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shops")
public class ShopController {

  private final ShopService shopService;

  @Secured({Authority.MASTER, Authority.MANAGER})
  @PostMapping
  public ResponseEntity<ApiResponse> createShop(
      @RequestBody ShopRequestDto shopRequestDto) {

    ShopResponseDto shopResponseDto = shopService.createShop(shopRequestDto);

    return ResponseEntity.status(HttpStatus.OK)
        .body(new ApiResponse("가게 생성 성공", HttpStatus.OK.value(), shopResponseDto));
  }


}