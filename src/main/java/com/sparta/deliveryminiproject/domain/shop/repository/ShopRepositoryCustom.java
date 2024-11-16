package com.sparta.deliveryminiproject.domain.shop.repository;

import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopRepositoryCustom {

  Page<Shop> searchShops(
      String searchQuery, Pageable pageable);

}
