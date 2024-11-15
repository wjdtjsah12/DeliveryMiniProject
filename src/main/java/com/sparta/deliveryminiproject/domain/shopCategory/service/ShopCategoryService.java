package com.sparta.deliveryminiproject.domain.shopCategory.service;

import com.sparta.deliveryminiproject.domain.category.entity.Category;
import com.sparta.deliveryminiproject.domain.category.repository.CategoryRepository;
import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import com.sparta.deliveryminiproject.domain.shopCategory.entity.ShopCategory;
import com.sparta.deliveryminiproject.domain.shopCategory.repository.ShopCategoryRepository;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopCategoryService {

  private final ShopCategoryRepository shopCategoryRepository;
  private final CategoryRepository categoryRepository;

  public void saveShopCategory(Shop shop, UUID categoryId) {

    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ApiException("존재하지 않는 카테고리 ID 입니다.", HttpStatus.BAD_REQUEST));

    shopCategoryRepository.save(new ShopCategory(shop, category));
  }

}
