package com.sparta.deliveryminiproject.domain.shop.service;

import com.sparta.deliveryminiproject.domain.region.entity.Region;
import com.sparta.deliveryminiproject.domain.region.repository.RegionRepository;
import com.sparta.deliveryminiproject.domain.shop.dto.ShopRequestDto;
import com.sparta.deliveryminiproject.domain.shop.dto.ShopResponseDto;
import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import com.sparta.deliveryminiproject.domain.shop.repository.ShopRepository;
import com.sparta.deliveryminiproject.domain.shopCategory.entity.ShopCategory;
import com.sparta.deliveryminiproject.domain.shopCategory.service.ShopCategoryService;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum;
import com.sparta.deliveryminiproject.domain.user.repository.UserRepository;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopService {

  private final ShopRepository shopRepository;
  private final UserRepository userRepository;
  private final RegionRepository regionRepository;
  private final ShopCategoryService shopCategoryService;

  @Transactional
  public ShopResponseDto createShop(ShopRequestDto shopRequestDto) {

    if (shopRepository.findShopByShopName(shopRequestDto.getShopName()).isPresent()) {
      throw new ApiException("중복된 가게 이름 입니다.", HttpStatus.BAD_REQUEST);
    }

    User owner = userRepository.findById(shopRequestDto.getUserId())
        .orElseThrow(() -> new ApiException("해당 사용자가 없습니다.", HttpStatus.NOT_FOUND));

    Region region = regionRepository.findById(shopRequestDto.getRegionId())
        .orElseThrow(() -> new ApiException("해당 지역이 존재하지 않습니다.", HttpStatus.NOT_FOUND));

    Shop shop = new Shop(shopRequestDto, owner, region);

    shopRepository.save(shop);

    // @Transactional 전이 (내부 호출 문제x)
    Set<ShopCategory> shopCategorySet = shopCategoryService.saveShopCategory(shop,
        shopRequestDto.getCategoryIdSet());

    Set<String> categoryNameSet = shopCategoryService.convertShopCategorySetToCategoryNameSet(
        shopCategorySet);

    return new ShopResponseDto(shop, categoryNameSet);
  }

  public ShopResponseDto getShop(UUID shopId) {

    Shop shop = shopRepository.findShopByIdOrElseThrow(shopId);

    if (shop.getIsDeleted() || shop.getIsHidden()) {
      throw new ApiException("삭제되거나 숨겨진 가게입니다.", HttpStatus.NOT_FOUND);
    }

    // 이렇게 쿼리 2번 나가게 되는 것이 연관관계 설정(mappedBy)보다 좋을 것인가...
    Set<String> categoryNameSet = shopCategoryService.getCategoryNameSet(shopId);

    return new ShopResponseDto(shop, categoryNameSet);
  }


  public Page<ShopResponseDto> getShopList(int size, String searchQuery, String sortBy,
      Direction direction, Integer page) {

    // size를 10, 30, 50로 제한
    size = (size == 30 || size == 50) ? size : 10;  // size가 30이나 50이 아니면 10으로 고정

    Pageable pageable = PageRequest.of(page, size, direction, sortBy);

    // "isDeleted"와 "isHidden"이 false만 조회  ->  queryDSL로 구현 완료
    Page<Shop> shopPage = shopRepository.searchShopsByKeyword(searchQuery, pageable);

    return shopPage.map(shop -> {
      Set<String> categoryNameSet = shopCategoryService.getCategoryNameSet(shop.getId());
      return new ShopResponseDto(shop, categoryNameSet);
    });
  }

  @Transactional
  public ShopResponseDto updateShop(UUID shopId, ShopRequestDto shopRequestDto, User user) {

    Shop shop = shopRepository.findShopByIdOrElseThrow(shopId);

    ShopService.validateShopOwner(user, shop);

    Region region = regionRepository.findById(shopRequestDto.getRegionId()).orElse(null);

    shop.update(shopRequestDto, region);

    Set<String> categoryNameSet = shopCategoryService.getCategoryNameSet(shopId);

    return new ShopResponseDto(shop, categoryNameSet);
  }

  @Transactional
  public ShopResponseDto deleteShop(UUID shopId) {

    Shop shop = shopRepository.findShopByIdOrElseThrow(shopId);

    shop.setIsDeleted(true);

    Set<String> categoryNameSet = shopCategoryService.getCategoryNameSet(shopId);

    return new ShopResponseDto(shop, categoryNameSet);
  }

  public Page<ShopResponseDto> getShopListByRegion(UUID regionId, int size, String sortBy,
      Direction direction, Integer page) {

    // size를 10, 30, 50로 제한
    size = (size == 30 || size == 50) ? size : 10;  // size가 30이나 50이 아니면 10으로 고정

    Pageable pageable = PageRequest.of(page, size, direction, sortBy);

    Page<Shop> shopPage = shopRepository.findShopsByRegionId(regionId, pageable);

    return shopPage.map(shop -> {
      Set<String> categoryNameSet = shopCategoryService.getCategoryNameSet(shop.getId());
      return new ShopResponseDto(shop, categoryNameSet);
    });
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
