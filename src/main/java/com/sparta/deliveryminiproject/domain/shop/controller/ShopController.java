package com.sparta.deliveryminiproject.domain.shop.controller;

import com.sparta.deliveryminiproject.domain.shop.dto.ShopRequestDto;
import com.sparta.deliveryminiproject.domain.shop.dto.ShopResponseDto;
import com.sparta.deliveryminiproject.domain.shop.service.ShopService;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum.Authority;
import com.sparta.deliveryminiproject.global.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shops")
public class ShopController {

  private final ShopService shopService;

  @Secured({Authority.MASTER, Authority.MANAGER})
  @PostMapping
  public ApiResponse createShop(
      @RequestBody ShopRequestDto shopRequestDto) {

    ShopResponseDto shopResponseDto = shopService.createShop(shopRequestDto);

    return ApiResponse.success(shopResponseDto);
  }

  @GetMapping("/{shopId}")
  public ApiResponse getShop(
      @PathVariable UUID shopId) {

    ShopResponseDto shopResponseDto = shopService.getShop(shopId);

    return ApiResponse.success(shopResponseDto);
  }

  @GetMapping
  public ApiResponse<Page<ShopResponseDto>> getShopList(
      @RequestParam(defaultValue = "10") int size,
      @RequestParam String keyword,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "DESC") Direction direction,
      Pageable pageable) {

    Page<ShopResponseDto> page = shopService.getShopList(size, keyword, sortBy, direction,
        pageable);

    return ApiResponse.success(page);
  }

  @Secured({Authority.OWNER, Authority.MASTER, Authority.MANAGER})
  @PutMapping("/{shopId}")
  public ApiResponse updateShop(
      @PathVariable UUID shopId,
      @Valid @RequestBody ShopRequestDto shopRequestDto) {

    ShopResponseDto shopResponseDto = shopService.updateShop(shopId, shopRequestDto);

    return ApiResponse.success(shopResponseDto);
  }

  @Secured({Authority.MASTER, Authority.MANAGER})
  @DeleteMapping("/{shopId}")
  public ApiResponse deleteShop(
      @PathVariable UUID shopId) {

    ShopResponseDto shopResponseDto = shopService.deleteShop(shopId);

    return ApiResponse.success(shopResponseDto);
  }


}
