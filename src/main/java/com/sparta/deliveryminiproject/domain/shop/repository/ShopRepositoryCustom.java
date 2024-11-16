package com.sparta.deliveryminiproject.domain.shop.repository;

import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopRepositoryCustom {

  Page<Shop> searchShopsByKeyword(
      String searchQuery, Pageable pageable);

  Page<Shop> findShopsByRegionId(UUID regionId, Pageable pageable);
}
