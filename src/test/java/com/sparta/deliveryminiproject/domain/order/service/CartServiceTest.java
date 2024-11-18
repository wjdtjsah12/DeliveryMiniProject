//package com.sparta.deliveryminiproject.domain.order.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//import com.sparta.deliveryminiproject.domain.cart.dto.CartRequestDto;
//import com.sparta.deliveryminiproject.domain.cart.entity.Cart;
//import com.sparta.deliveryminiproject.domain.cart.repository.CartRepository;
//import com.sparta.deliveryminiproject.domain.cart.service.CartService;
//import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
//import com.sparta.deliveryminiproject.domain.menu.repository.MenuRepository;
//import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
//import com.sparta.deliveryminiproject.domain.shop.repository.ShopRepository;
//import com.sparta.deliveryminiproject.domain.user.entity.User;
//import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum;
//import com.sparta.deliveryminiproject.domain.user.repository.UserRepository;
//import com.sparta.deliveryminiproject.global.exception.ApiException;
//import java.util.List;
//import java.util.UUID;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//class CartServiceTest {
//
//  @Autowired
//  private CartService cartService;
//
//  @Autowired
//  private CartRepository cartRepository;
//
//  @Autowired
//  private UserRepository userRepository;
//
//  @Autowired
//  private MenuRepository menuRepository;
//
//  @Autowired
//  private ShopRepository shopRepository;
//
//  @DisplayName("유저 정보를 받아 카트를 조회한다.")
//  @Test
//  void findMenuListInCartWithUser() {
////    // given
////    User user = new User("aaaa", "aaaa", "User");
////    userRepository.save(user);
////
////    Shop shop = new Shop("맥도날드", "햄버거가게", 10000, 1000);
////    shopRepository.save(shop);
////
////    Menu menu1 = Menu.builder()
////        .menuName("빅맥")
////        .description("햄버거1")
////        .price(5000)
////        .shop(shop)
////        .build();
////
////    Menu menu2 = Menu.builder()
////        .menuName("상하이 스파이시 버거")
////        .description("햄버거2")
////        .price(6000)
////        .shop(shop)
////        .build();
////
////    menuRepository.saveAll(List.of(menu1, menu2));
////
////    Cart cart1 = Cart.builder()
////        .user(user)
////        .shop(shop)
////        .menu(menu1)
////        .quantity(1)
////        .build();
////
////    Cart cart2 = Cart.builder()
////        .user(user)
////        .shop(shop)
////        .menu(menu2)
////        .quantity(2)
////        .build();
////
////    Cart cart3 = Cart.builder()
////        .user(user)
////        .shop(shop)
////        .menu(menu2)
////        .quantity(2)
////        .build();
////
////    cart3.setIsDeleted(true);
////
////    cartRepository.saveAll(List.of(cart1, cart2, cart3));
////
////    // when
////    CartResponse cartResponse = cartService.getCart(user);
////
////    // then
////    System.out.println("가게 이름 = " + cartResponse.getShopName());
////    for (CartMenuResponse cartMenuResponse : cartResponse.getMenuList()) {
////      System.out.println("메뉴 이름 = " + cartMenuResponse.getMenuName());
////      System.out.println("수량 = " + cartMenuResponse.getQuantity());
////      System.out.println("가격 = " + cartMenuResponse.getPrice());
////    }
////    System.out.println("메뉴 가격 = " + cartResponse.getTotalMenuPrice());
////    System.out.println("배달팁 = " + cartResponse.getDeliveryTip());
////    System.out.println("총 가격 = " + cartResponse.getTotalPrice());
////    System.out.println("cartResponse.isAvailableToOrder() = " + cartResponse.isAvailableToOrder());
//  }
//
//  @DisplayName("유저의 장바구니가 비었을 때 메뉴를 카트에 담으면 저장된다")
//  @Test
//  void addCartWithEmptyCart() {
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
//
//    menuRepository.saveAll(List.of(menu1, menu2));
//
//    // when
//    CartRequestDto cartRequestDto = CartRequestDto.builder()
//        .menuId(menu1.getId())
//        .quantity(2)
//        .build();
//
//    Cart cart = cartService.addMenuToCart(user, cartRequestDto);
//
//    // then
//
//    List<Cart> cartList = cartRepository.findAllByUserAndIsDeletedFalse(user);
//    assertThat(cartList.get(0).getId()).isEqualTo(cart.getId());
//  }
//
//  @DisplayName("유저의 장바구니가 비어있지 않을 떄 이미 포함된 가게의 메뉴를 추가하면 장바구니에 저장된다")
//  @Test
//  void addMenuInCartWithAlreadyToCart() {
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
//
//    Menu menu3 = Menu.builder()
//        .menuName("상하이 스파이시 버거")
//        .description("햄버거3")
//        .price(7000)
//        .shop(shop)
//        .build();
//
//    menuRepository.saveAll(List.of(menu1, menu2, menu3));
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
//    cartRepository.saveAll(List.of(cart1, cart2));
//
//    // when
//    CartRequestDto cartRequestDto = CartRequestDto.builder()
//        .menuId(menu3.getId())
//        .quantity(1)
//        .build();
//
//    // then
//    Cart cart = cartService.addMenuToCart(user, cartRequestDto);
//    List<UUID> uuidList = cartRepository.findAllByUserAndIsDeletedFalse(user).stream()
//        .map(c -> c.getId())
//        .toList();
//    assertThat(uuidList).contains(cart.getId());
//  }
//
//  @DisplayName("유저의 장바구니가 비어있지 않을 때 다른 가게의 메뉴를 장바구니에 추가하면 예외가 발생한다")
//  @Test
//  void addMenuToCartWithAnotherShop() {
//    // given
//    User user = new User("aaaa", "aaaa", UserRoleEnum.CUSTOMER);
//    userRepository.save(user);
//
//    Shop shop1 = new Shop("맥도날드", "햄버거가게", 10000, 1000);
//    Shop shop2 = new Shop("중국집", "그냥 흔한 중국집", 13000, 0);
//    shopRepository.saveAll(List.of(shop1, shop2));
//
//    Menu menu1 = Menu.builder()
//        .menuName("빅맥")
//        .description("햄버거1")
//        .price(5000)
//        .shop(shop1)
//        .build();
//
//    Menu menu2 = Menu.builder()
//        .menuName("상하이 스파이시 버거")
//        .description("햄버거2")
//        .price(6000)
//        .shop(shop1)
//        .build();
//
//    Menu menu3 = Menu.builder()
//        .menuName("짜장면")
//        .description("짜장면1")
//        .price(6000)
//        .shop(shop2)
//        .build();
//
//    menuRepository.saveAll(List.of(menu1, menu2, menu3));
//
//    Cart cart1 = Cart.builder()
//        .user(user)
//        .shop(shop1)
//        .menu(menu1)
//        .quantity(1)
//        .build();
//
//    Cart cart2 = Cart.builder()
//        .user(user)
//        .shop(shop1)
//        .menu(menu2)
//        .quantity(2)
//        .build();
//
//    cartRepository.saveAll(List.of(cart1, cart2));
//
//    // when
//    CartRequestDto cartRequestDto = CartRequestDto.builder()
//        .menuId(menu3.getId())
//        .quantity(1)
//        .build();
//
//    // then
//    assertThatThrownBy(() -> cartService.addMenuToCart(user, cartRequestDto))
//        .isInstanceOf(ApiException.class);
//  }
//
//  @DisplayName("이미 장바구니에 담겨있는 똑같은 메뉴를 한번 더 추가하면 수량이 늘어난다")
//  @Test
//  void addExistMenu() {
//    // given
//    User user = new User("aaaa", "aaaa", UserRoleEnum.CUSTOMER);
//    userRepository.save(user);
//
//    Shop shop1 = new Shop("맥도날드", "햄버거가게", 10000, 1000);
//    Shop shop2 = new Shop("중국집", "그냥 흔한 중국집", 13000, 0);
//    shopRepository.saveAll(List.of(shop1, shop2));
//
//    Menu menu1 = Menu.builder()
//        .menuName("빅맥")
//        .description("햄버거1")
//        .price(5000)
//        .shop(shop1)
//        .build();
//
//    Menu menu2 = Menu.builder()
//        .menuName("상하이 스파이시 버거")
//        .description("햄버거2")
//        .price(6000)
//        .shop(shop1)
//        .build();
//
//    menuRepository.saveAll(List.of(menu1, menu2));
//
//    Cart cart1 = Cart.builder()
//        .user(user)
//        .shop(shop1)
//        .menu(menu1)
//        .quantity(1)
//        .build();
//
//    Cart cart2 = Cart.builder()
//        .user(user)
//        .shop(shop1)
//        .menu(menu2)
//        .quantity(2)
//        .build();
//
//    cartRepository.saveAll(List.of(cart1, cart2));
//
//    // when
//    CartRequestDto cartRequestDto = CartRequestDto.builder()
//        .menuId(menu1.getId())
//        .quantity(2)
//        .build();
//
//    // then
//    Cart cart = cartService.addMenuToCart(user, cartRequestDto);
//    assertThat(cart.getQuantity()).isEqualTo(3);
//  }
//}