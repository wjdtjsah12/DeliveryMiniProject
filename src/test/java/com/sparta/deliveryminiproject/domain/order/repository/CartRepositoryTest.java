package com.sparta.deliveryminiproject.domain.order.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.deliveryminiproject.domain.cart.entity.Cart;
import com.sparta.deliveryminiproject.domain.cart.repository.CartRepository;
import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import com.sparta.deliveryminiproject.domain.menu.repository.MenuRepository;
import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import com.sparta.deliveryminiproject.domain.shop.repository.ShopRepository;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum;
import com.sparta.deliveryminiproject.domain.user.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CartRepositoryTest {

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MenuRepository menuRepository;

  @Autowired
  private ShopRepository shopRepository;

//  @DisplayName("유저의 카트를 조회했을 때 삭제되지 않은 카트에 제품들을 가져올 수 있다.")
//  @Test
//  void findCart() {
//    // given
//    User user = new User("aaaa", "aaaa", UserRoleEnum.CUSTOMER);
//    userRepository.save(user);
//
//    Shop shop = new Shop("맥도날드", "햄버거가게", 10000, 1000);
//    shopRepository.save(shop);
//
//    Menu menu1 = Menu.builder()
//        .menuName("빅맥")
//        .description("햄버거1")
//        .price(5000)
//        .shop(shop)
//        .build();
//
//    Menu menu2 = Menu.builder()
//        .menuName("상하이 스파이시 버거")
//        .description("햄버거2")
//        .price(6000)
//        .shop(shop)
//        .build();
//    menuRepository.saveAll(List.of(menu1, menu2));
//
//    Cart cart1 = Cart.builder()
//        .user(user)
//        .shop(shop)
//        .menu(menu1)
//        .quantity(1)
//        .build();
//
//    Cart cart2 = Cart.builder()
//        .user(user)
//        .shop(shop)
//        .menu(menu2)
//        .quantity(2)
//        .build();
//
//    Cart cart3 = Cart.builder()
//        .user(user)
//        .shop(shop)
//        .menu(menu2)
//        .quantity(2)
//        .build();
//
//    cart3.setIsDeleted(true);
//
//    cartRepository.saveAll(List.of(cart1, cart2, cart3));
//
//    // when
//    List<Cart> result = cartRepository.findAllByUserAndIsDeletedFalse(user);
//
//    // then
//    assertThat(result).hasSize(2);
//  }
}