package com.sparta.deliveryminiproject.domain.shop.service;

import com.sparta.deliveryminiproject.domain.shop.dto.ShopRequestDto;
import com.sparta.deliveryminiproject.domain.shop.dto.ShopResponseDto;
import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import com.sparta.deliveryminiproject.domain.shop.repository.ShopRepository;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum;
import com.sparta.deliveryminiproject.domain.user.repository.UserRepository;
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

  public ShopResponseDto getShop(UUID shopId) {

    Shop shop = shopRepository.findById(shopId)
        .orElseThrow(() -> new ApiException("존재하지 않는 가게입니다.", HttpStatus.NOT_FOUND));

    if (shop.getIsDeleted() || shop.getIsHidden()) {
      throw new ApiException("삭제되거나 숨겨진 가게입니다.", HttpStatus.NOT_FOUND);
    }

    return new ShopResponseDto(shop);
  }


  public Page<ShopResponseDto> getShopList(int size, String searchQuery, String sortBy,
      Direction direction, Pageable pageable) {

    Sort sort = Sort.by(direction, sortBy);
    pageable = PageRequest.of(pageable.getPageNumber(), size, sort);

    // size를 10, 30, 50로 제한
    size = (size == 30 || size == 50) ? size : 10;  // size가 30이나 50이 아니면 10으로 고정

    pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());

    // "isDeleted"와 "isHidden"이 false만 조회  ->  추후 queryDSL로 구현 예정
    return shopRepository.findByShopNameContainingIgnoreCaseAndIsDeletedFalseAndIsHiddenFalse(
            searchQuery, pageable)
        .map(ShopResponseDto::new);
  }

  @Transactional
  public ShopResponseDto updateShop(UUID shopId, ShopRequestDto shopRequestDto, User user) {

    Shop shop = shopRepository.findById(shopId)
        .orElseThrow(() -> new ApiException("존재하지 않는 가게입니다.", HttpStatus.NOT_FOUND));

    ShopService.validateShopOwner(user, shop);

    shop.update(shopRequestDto);

    return new ShopResponseDto(shop);
  }

  @Transactional
  public ShopResponseDto deleteShop(UUID shopId) {

    Shop shop = shopRepository.findById(shopId)
        .orElseThrow(() -> new ApiException("존재하지 않는 가게입니다.", HttpStatus.NOT_FOUND));

    shop.setIsDeleted(true);

    return new ShopResponseDto(shop);
  }

  // 해당 유저가 가게 소유주 이상의 권한을 갖고 있는지 검증
  public static void validateShopOwner(User user, Shop shop) {
    if (!(user.getRole().equals(UserRoleEnum.MANAGER) ||
        user.getRole().equals(UserRoleEnum.MASTER))) {
      if (!shop.getUser().getId().equals(user.getId())) {
        throw new ApiException("가게 주인이 아닙니다.", HttpStatus.BAD_REQUEST);
      }
    }
  }
}
