package com.sparta.deliveryminiproject.domain.order.service;

import com.sparta.deliveryminiproject.domain.order.dto.CartMenuResponseDto;
import com.sparta.deliveryminiproject.domain.order.dto.CartRequestDto;
import com.sparta.deliveryminiproject.domain.order.dto.CartResponseDto;
import com.sparta.deliveryminiproject.domain.order.entity.Cart;
import com.sparta.deliveryminiproject.domain.order.repository.CartRepository;
import com.sparta.deliveryminiproject.domain.shop.entity.Menu;
import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import com.sparta.deliveryminiproject.domain.shop.repository.MenuRepository;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {

  private final CartRepository cartRepository;

  private final MenuRepository menuRepository;

  @Transactional
  public Cart addMenu(User user, CartRequestDto cartRequestDto) {

    Menu menu = menuRepository.findById(cartRequestDto.getMenuId())
        .orElseThrow(() -> new NullPointerException("선택하신 메뉴의 정보가 없습니다."));

    Shop shop = menu.getShop();

    List<Cart> cartList = cartRepository.findAllByUserAndIsDeletedFalse(user);

    if (!cartList.isEmpty()) {
      Shop existingShop = cartList.get(0).getShop();

      if (!existingShop.getId().equals(shop.getId())) {
        throw new IllegalArgumentException("한 번에 하나의 가게에서만 주문할 수 있습니다.");
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

  public Optional<CartResponseDto> getCart(User user) {

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
  public void updateQuantity(User user, UUID cartId, String sign) {

    Cart cart = cartRepository.findByIdAndUserAndIsDeletedFalse(cartId, user)
        .orElseThrow(() -> new NullPointerException("장바구니에 담긴 메뉴의 정보를 찾을 수 없습니다."));

    if (sign.equals("plus")) {
      cart.updateQuantity(1);
    } else if (sign.equals("minus")) {
      if (cart.getQuantity() == 0) {
        cart.setIsDeleted(true);
        return;
      }
      cart.updateQuantity(-1);
    }
  }

  @Transactional
  public void deleteCart(User user, UUID cartId) {

    Cart cart = cartRepository.findByIdAndUserAndIsDeletedFalse(cartId, user)
        .orElseThrow(() -> new NullPointerException("장바구니에 담긴 메뉴의 정보를 찾을 수 없습니다."));

    cart.setIsDeleted(true);
  }

  private boolean availableToOrder(int totalPrice, Shop shop) {
    return totalPrice > shop.getMinDeliveryPrice();
  }
}
