package com.sparta.deliveryminiproject.domain.shop.repository;

import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {

  Optional<Shop> findShopByShopName(String shopName);
}
