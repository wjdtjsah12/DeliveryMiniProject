package com.sparta.deliveryminiproject.domain.shop.repository;

import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

public interface ShopRepository extends JpaRepository<Shop, UUID>, ShopRepositoryCustom {

  Optional<Shop> findShopByShopName(String shopName);

  default Shop findShopByIdOrElseThrow(UUID id) {
    return findById(id).orElseThrow(
        () -> new ApiException("존재하지 않는 가게 ID 입니다.", HttpStatus.NOT_FOUND)
    );
  }

}
