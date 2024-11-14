package com.sparta.deliveryminiproject.domain.menu.service;

import com.sparta.deliveryminiproject.domain.menu.dto.MenuRequestDto;
import com.sparta.deliveryminiproject.domain.menu.dto.MenuResponseDto;
import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import com.sparta.deliveryminiproject.domain.menu.repository.MenuRepository;
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
public class MenuService {

  private final MenuRepository menuRepository;
  private final ShopRepository shopRepository;

  public MenuResponseDto createMenu(MenuRequestDto menuRequestDto, UUID shopId, User user) {

    Shop shop = shopRepository.findById(shopId)
        .orElseThrow(() -> new ApiException("존재하지 않는 가게 ID 입니다.", HttpStatus.BAD_REQUEST));

    // 가게 소유자만 가게 정보를 수정 할 수 있도록 검증
    if (!(user.getRole().equals(UserRoleEnum.MANAGER) ||
        user.getRole().equals(UserRoleEnum.MASTER))) {
      if (!shop.getUser().getId().equals(user.getId())) {
        throw new ApiException("가게 주인이 아닙니다.", HttpStatus.BAD_REQUEST);
      }
    }

    Menu menu = new Menu(menuRequestDto, shop);

    menuRepository.save(menu);

    return new MenuResponseDto(menu);
  }

  public MenuResponseDto getMenu(UUID menuId, UUID shopId) {

    shopRepository.findById(shopId)
        .orElseThrow(() -> new ApiException("존재하지 않는 가게 ID 입니다.", HttpStatus.BAD_REQUEST));

    Menu menu = menuRepository.findById(menuId)
        .orElseThrow(() -> new ApiException("존재하지 않는 메뉴 ID 입니다.", HttpStatus.BAD_REQUEST));

    return new MenuResponseDto(menu);
  }

  public Page<MenuResponseDto> getMenuList(UUID shopId, int size, String searchQuery, String sortBy,
      Direction direction, Pageable pageable) {

    Sort sort = Sort.by(direction, sortBy);
    pageable = PageRequest.of(pageable.getPageNumber(), size, sort);

    // size를 10, 30, 50로 제한
    size = (size == 30 || size == 50) ? size : 10;  // size가 30이나 50이 아니면 10으로 고정

    pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());

    Shop shop = shopRepository.findById(shopId)
        .orElseThrow(() -> new ApiException("존재하지 않는 가게 ID 입니다.", HttpStatus.BAD_REQUEST));

    Page<MenuResponseDto> pagedMenuResponseDtoList
        = menuRepository.findByMenuNameContainingIgnoreCaseAndIsDeletedFalseAndIsHiddenFalseAndShopId(
            searchQuery, pageable, shopId)
        .map(MenuResponseDto::new);

    return pagedMenuResponseDtoList;
  }

  @Transactional
  public MenuResponseDto updateMenu(MenuRequestDto menuRequestDto, UUID shopId, UUID menuId,
      User user) {

    Shop shop = shopRepository.findById(shopId)
        .orElseThrow(() -> new ApiException("존재하지 않는 가게 ID 입니다.", HttpStatus.BAD_REQUEST));

    Menu menu = menuRepository.findById(menuId)
        .orElseThrow(() -> new ApiException("존재하지 않는 메뉴 ID 입니다.", HttpStatus.BAD_REQUEST));

    // 가게 소유자만 가게 정보를 수정 할 수 있도록 검증
    if (!(user.getRole().equals(UserRoleEnum.MANAGER) ||
        user.getRole().equals(UserRoleEnum.MASTER))) {
      if (!shop.getUser().getId().equals(user.getId())) {
        throw new ApiException("가게 주인이 아닙니다.", HttpStatus.BAD_REQUEST);
      }
    }

    menu.update(menuRequestDto);

    return new MenuResponseDto(menu);
  }

  @Transactional
  public MenuResponseDto deleteMenu(UUID shopId, UUID menuId, User user) {

    Shop shop = shopRepository.findById(shopId)
        .orElseThrow(() -> new ApiException("존재하지 않는 가게 ID 입니다.", HttpStatus.BAD_REQUEST));

    Menu menu = menuRepository.findById(menuId)
        .orElseThrow(() -> new ApiException("존재하지 않는 메뉴 ID 입니다.", HttpStatus.BAD_REQUEST));

    // 가게 소유자만 가게 정보를 수정 할 수 있도록 검증
    if (!(user.getRole().equals(UserRoleEnum.MANAGER) ||
        user.getRole().equals(UserRoleEnum.MASTER))) {
      if (!shop.getUser().getId().equals(user.getId())) {
        throw new ApiException("가게 주인이 아닙니다.", HttpStatus.BAD_REQUEST);
      }
    }

    menu.setIsDeleted(true);

    return new MenuResponseDto(menu);
  }
}
