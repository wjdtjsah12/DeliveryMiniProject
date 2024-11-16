package com.sparta.deliveryminiproject.domain.order.repository;

import com.sparta.deliveryminiproject.domain.order.entity.Order;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID>, CustomOrderRepository {

  @EntityGraph(attributePaths = {"cartList", "cartList.shop", "cartList.menu", "payment"})
  Optional<Order> findByIdAndUserAndIsDeletedFalse(UUID orderId, User user);
}
