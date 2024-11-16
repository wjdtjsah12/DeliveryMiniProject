package com.sparta.deliveryminiproject.domain.cart.repository;

import com.sparta.deliveryminiproject.domain.cart.entity.Cart;
import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import com.sparta.deliveryminiproject.domain.order.entity.Order;
import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, UUID> {

  @Query("select c from Cart c join fetch c.menu m join fetch c.shop s where c.user = :user and c.isDeleted = false order by c.createdAt asc")
  List<Cart> findAllByUserAndIsDeletedFalse(@Param("user") User user);

  Optional<Cart> findByIdAndUserAndIsDeletedFalse(UUID id, User user);

  Optional<Cart> findByUserAndMenuAndIsDeletedFalse(User user, Menu menu);

  boolean existsByUserAndShopNotAndIsDeletedFalse(User user, Shop shop);

  List<Cart> findAllByOrder(Order order);
}
