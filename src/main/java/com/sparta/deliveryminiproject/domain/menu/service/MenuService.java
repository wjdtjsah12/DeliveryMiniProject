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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
}
