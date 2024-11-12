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
import java.util.ArrayList;
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

    Optional<Cart> cartOptional = cartRepository.findByUserAndMenuAndIsDeletedFalse(
        user, menu);

    if (cartOptional.isPresent()) {
      Cart cart = cartOptional.get();
      cart.updateQuantity(cartRequestDto.getQuantity());
      return cart;
    }

    boolean hasOtherShop = cartRepository.existsByUserAndShopNotAndIsDeletedFalse(user,
        menu.getShop());
    if (hasOtherShop) {
      throw new ApiException("한 번에 하나의 가게에서만 주문할 수 있습니다.", HttpStatus.BAD_REQUEST);
    }

    return cartRepository.save(cartRequestDto.toEntity(user, menu));
  }

  public CartResponseDto findMenuListInCart(User user) {
    List<Cart> cartList = cartRepository.findAllByUserAndIsDeletedFalse(user);

    if (cartList.isEmpty()) {
      return null;
    }

    Shop shop = cartList.get(0).getShop();

    List<CartMenuResponseDto> menuResponseList = new ArrayList<>();
    int totalMenuPrice = cartList.stream()
        .map(cart -> {
          CartMenuResponseDto dto = CartMenuResponseDto.from(cart);
          menuResponseList.add(dto);
          return dto.getPrice();
        })
        .mapToInt(Integer::intValue)
        .sum();

    return CartResponseDto.builder()
        .shopName(shop.getShopName())
        .menuList(menuResponseList)
        .availableToOrder(availableToOrder(totalMenuPrice, shop))
        .totalMenuPrice(totalMenuPrice)
        .deliveryTip(shop.getDeliveryTip())
        .totalPrice(totalMenuPrice + shop.getDeliveryTip())
        .build();
  }

  @Transactional
  public void updateQuantity(User user, UUID cartId, String operator) {
    Cart cart = cartRepository.findByIdAndUserAndIsDeletedFalse(cartId, user)
        .orElseThrow(() -> new ApiException("장바구니에 담긴 메뉴의 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

    switch (operator) {
      case "plus" -> increaseQuantity(cart);
      case "minus" -> decreaseQuantity(cart);
      default -> throw new ApiException("잘못된 연산자입니다. (plus/minus 만 허용)", HttpStatus.BAD_REQUEST);
    }
  }

  @Transactional
  public void deleteMenuAtCart(User user, UUID cartId) {
    Cart cart = cartRepository.findByIdAndUserAndIsDeletedFalse(cartId, user)
        .orElseThrow(() -> new ApiException("장바구니에 담긴 메뉴의 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

    cart.setIsDeleted(true);
  }

  private void increaseQuantity(Cart cart) {
    cart.updateQuantity(1);
  }

  private void decreaseQuantity(Cart cart) {
    if (cart.getQuantity() <= 1) {
      cart.setIsDeleted(true);
    } else {
      cart.updateQuantity(-1);
    }
  }

  private boolean availableToOrder(int totalPrice, Shop shop) {
    return totalPrice > shop.getMinDeliveryPrice();
  }
}
