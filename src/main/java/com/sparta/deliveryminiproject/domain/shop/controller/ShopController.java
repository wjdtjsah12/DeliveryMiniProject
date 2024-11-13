package com.sparta.deliveryminiproject.domain.shop.controller;

import com.sparta.deliveryminiproject.domain.shop.dto.ShopRequestDto;
import com.sparta.deliveryminiproject.domain.shop.dto.ShopResponseDto;
import com.sparta.deliveryminiproject.domain.shop.service.ShopService;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum.Authority;
import com.sparta.deliveryminiproject.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/shops")
public class ShopController {

  private final ShopService shopService;

  @Secured({Authority.MASTER, Authority.MANAGER})
  @PostMapping
  public ResponseEntity createShop(
      @ModelAttribute ShopRequestDto shopRequestDto) {

    ShopResponseDto shopResponseDto = shopService.createShop(shopRequestDto);

    return ResponseEntity.ok(shopResponseDto);
  }

  @GetMapping("/{shopId}")
  public ResponseEntity getShop(
      @PathVariable UUID shopId) {

    ShopResponseDto shopResponseDto = shopService.getShop(shopId);

    return ResponseEntity.ok(shopResponseDto);
  }

  @GetMapping
  public ResponseEntity<Page<ShopResponseDto>> getShopList(
      @RequestParam(defaultValue = "10") int size,
      @RequestParam String searchQuery,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "DESC") Direction direction,
      Pageable pageable) {

    Page<ShopResponseDto> page = shopService.getShopList(size, searchQuery, sortBy, direction,
        pageable);

    return ResponseEntity.ok(page);
  }

  @Secured({Authority.OWNER, Authority.MASTER, Authority.MANAGER})
  @PutMapping("/{shopId}")
  public ResponseEntity updateShop(
      @PathVariable UUID shopId,
      @Valid @ModelAttribute ShopRequestDto shopRequestDto,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    ShopResponseDto shopResponseDto = shopService.updateShop(shopId, shopRequestDto,
        userDetails.getUser());

    return ResponseEntity.ok(shopResponseDto);
  }

  @Secured({Authority.MASTER, Authority.MANAGER})
  @DeleteMapping("/{shopId}")
  public ResponseEntity deleteShop(
      @PathVariable UUID shopId) {

    ShopResponseDto shopResponseDto = shopService.deleteShop(shopId);

    return ResponseEntity.ok(shopResponseDto);
  }


}
