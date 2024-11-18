package com.sparta.deliveryminiproject.domain.shopCategory.repository;

import com.sparta.deliveryminiproject.domain.shopCategory.entity.ShopCategory;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopCategoryRepository extends JpaRepository<ShopCategory, UUID>,
    ShopCategoryRepositoryCustom {

}