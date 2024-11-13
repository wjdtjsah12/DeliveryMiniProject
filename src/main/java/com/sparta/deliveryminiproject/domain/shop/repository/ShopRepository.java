package com.sparta.deliveryminiproject.domain.shop.repository;

import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, UUID> {

  Optional<Shop> findShopByShopName(String shopName);

  Page<Shop> findByShopNameContainingIgnoreCase(String keyword, Pageable pageable);
}