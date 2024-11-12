package com.sparta.deliveryminiproject.domain.cart.service;

import com.sparta.deliveryminiproject.domain.cart.dto.CartMenuResponseDto;
import com.sparta.deliveryminiproject.domain.cart.dto.CartRequestDto;
import com.sparta.deliveryminiproject.domain.cart.dto.CartResponseDto;
import com.sparta.deliveryminiproject.domain.cart.entity.Cart;
import com.sparta.deliveryminiproject.domain.cart.repository.CartRepository;
import com.sparta.deliveryminiproject.domain.shop.entity.Menu;
import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import com.sparta.deliveryminiproject.domain.shop.repository.MenuRepository;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

  private final CartRepository cartRepository;

  private final MenuRepository menuRepository;

  @Transactional
  public Cart addMenuToCart(User user, CartRequestDto cartRequestDto) {

    Menu menu = menuRepository.findById(cartRequestDto.getMenuId())
        .orElseThrow(() -> new ApiException("선택하신 메뉴의 정보가 없습니다.", HttpStatus.BAD_REQUEST));

    Shop shop = menu.getShop();

    List<Cart> cartList = cartRepository.findAllByUserAndIsDeletedFalse(user);

    if (!cartList.isEmpty()) {
      Shop existingShop = cartList.get(0).getShop();

      if (!existingShop.getId().equals(shop.getId())) {
        throw new ApiException("한 번에 하나의 가게에서만 주문할 수 있습니다.", HttpStatus.BAD_REQUEST);
      }
    }

    Cart existingCart = cartList.stream()
        .filter(cart -> cart.getMenu().getId().equals(menu.getId()))
        .findFirst()
        .orElse(null);

    if (existingCart != null) {
      existingCart.updateQuantity(cartRequestDto.getQuantity());
      return existingCart;
    } else {
      return cartRepository.save(cartRequestDto.toEntity(user, menu));
    }
  }

  public Optional<CartResponseDto> findMenuListInCart(User user) {

    List<Cart> cartList = cartRepository.findAllByUserAndIsDeletedFalse(user);

    if (cartList.isEmpty()) {
      return Optional.empty();
    }

    Shop shop = cartList.get(0).getShop();

    List<CartMenuResponseDto> menuResponseList = cartList.stream()
        .map(CartMenuResponseDto::from)
        .toList();

    int totalMenuPrice = menuResponseList.stream()
        .mapToInt(CartMenuResponseDto::getPrice)
        .sum();

    CartResponseDto cartResponseDto = CartResponseDto.builder()
        .shopName(shop.getShopName())
        .menuList(menuResponseList)
        .availableToOrder(availableToOrder(totalMenuPrice, shop))
        .totalMenuPrice(totalMenuPrice)
        .deliveryTip(shop.getDeliveryTip())
        .totalPrice(totalMenuPrice + shop.getDeliveryTip())
        .build();

    return Optional.of(cartResponseDto);
  }

  @Transactional
  public void updateQuantity(User user, UUID cartId, String operator) {

    Cart cart = cartRepository.findByIdAndUserAndIsDeletedFalse(cartId, user)
        .orElseThrow(() -> new ApiException("장바구니에 담긴 메뉴의 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

    if (operator.equals("plus")) {
      cart.updateQuantity(1);
    } else if (operator.equals("minus")) {
      if (cart.getQuantity() == 0) {
        cart.setIsDeleted(true);
        return;
      }
      cart.updateQuantity(-1);
    }
  }

  @Transactional
  public void deleteMenuAtCart(User user, UUID cartId) {

    Cart cart = cartRepository.findByIdAndUserAndIsDeletedFalse(cartId, user)
        .orElseThrow(() -> new ApiException("장바구니에 담긴 메뉴의 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

    cart.setIsDeleted(true);
  }

  private boolean availableToOrder(int totalPrice, Shop shop) {
    return totalPrice > shop.getMinDeliveryPrice();
  }
}
