package com.sparta.deliveryminiproject.domain.menu.service;

import com.sparta.deliveryminiproject.domain.menu.dto.MenuRequestDto;
import com.sparta.deliveryminiproject.domain.menu.dto.MenuResponseDto;
import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import com.sparta.deliveryminiproject.domain.menu.repository.MenuRepository;
import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import com.sparta.deliveryminiproject.domain.shop.repository.ShopRepository;
import com.sparta.deliveryminiproject.domain.shop.service.ShopService;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {

  private final MenuRepository menuRepository;
  private final ShopRepository shopRepository;

  public MenuResponseDto createMenu(MenuRequestDto menuRequestDto, UUID shopId, User user) {

    Shop shop = shopRepository.findShopByIdOrElseThrow(shopId);

    ShopService.validateShopOwner(user, shop);

    Menu menu = new Menu(menuRequestDto, shop);

    menuRepository.save(menu);

    return new MenuResponseDto(menu);
  }

  public MenuResponseDto getMenu(UUID menuId, UUID shopId) {

    Shop shop = shopRepository.findShopByIdOrElseThrow(shopId);

    Menu menu = menuRepository.findMenuByIdOrElseThrow(menuId);

    return new MenuResponseDto(menu);
  }

  public Page<MenuResponseDto> getMenuList(UUID shopId, int size, String searchQuery, String sortBy,
      Direction direction, Pageable pageable) {

    Sort sort = Sort.by(direction, sortBy);
    pageable = PageRequest.of(pageable.getPageNumber(), size, sort);

    // size를 10, 30, 50로 제한
    size = (size == 30 || size == 50) ? size : 10;  // size가 30이나 50이 아니면 10으로 고정

    pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());

    Shop shop = shopRepository.findShopByIdOrElseThrow(shopId);

    Page<MenuResponseDto> pagedMenuResponseDtoList
        = menuRepository.findByMenuNameContainingIgnoreCaseAndIsDeletedFalseAndIsHiddenFalseAndShopId(
            searchQuery, pageable, shopId)
        .map(MenuResponseDto::new);

    return pagedMenuResponseDtoList;
  }

  @Transactional
  public MenuResponseDto updateMenu(MenuRequestDto menuRequestDto, UUID shopId, UUID menuId,
      User user) {

    Shop shop = shopRepository.findShopByIdOrElseThrow(shopId);

    Menu menu = menuRepository.findMenuByIdOrElseThrow(menuId);

    ShopService.validateShopOwner(user, shop);

    menu.update(menuRequestDto);

    return new MenuResponseDto(menu);
  }

  @Transactional
  public MenuResponseDto deleteMenu(UUID shopId, UUID menuId, User user) {

    Shop shop = shopRepository.findShopByIdOrElseThrow(shopId);

    Menu menu = menuRepository.findMenuByIdOrElseThrow(menuId);

    ShopService.validateShopOwner(user, shop);

    menu.setIsDeleted(true);

    return new MenuResponseDto(menu);
  }
}
