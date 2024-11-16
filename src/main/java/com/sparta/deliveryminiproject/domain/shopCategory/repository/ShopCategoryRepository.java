package com.sparta.deliveryminiproject.domain.shopCategory.repository;

import com.sparta.deliveryminiproject.domain.shopCategory.entity.ShopCategory;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopCategoryRepository extends JpaRepository<ShopCategory, UUID> {

  @Query("SELECT sc FROM ShopCategory sc JOIN FETCH sc.category WHERE sc.shop.id = :shopId")
  Set<ShopCategory> findAllByShopId(@Param("shopId") UUID shopId);
}