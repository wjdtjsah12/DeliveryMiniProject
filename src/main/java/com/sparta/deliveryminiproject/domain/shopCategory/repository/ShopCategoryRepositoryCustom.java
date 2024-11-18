package com.sparta.deliveryminiproject.domain.shopCategory.repository;

import com.sparta.deliveryminiproject.domain.shopCategory.entity.ShopCategory;
import java.util.Set;
import java.util.UUID;

public interface ShopCategoryRepositoryCustom {

  Set<ShopCategory> findAllByShopId(UUID shopId);

}
