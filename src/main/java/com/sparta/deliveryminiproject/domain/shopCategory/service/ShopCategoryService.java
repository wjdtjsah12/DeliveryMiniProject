package com.sparta.deliveryminiproject.domain.shopCategory.service;

import com.sparta.deliveryminiproject.domain.category.entity.Category;
import com.sparta.deliveryminiproject.domain.category.repository.CategoryRepository;
import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import com.sparta.deliveryminiproject.domain.shopCategory.entity.ShopCategory;
import com.sparta.deliveryminiproject.domain.shopCategory.repository.ShopCategoryRepository;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopCategoryService {

  private final ShopCategoryRepository shopCategoryRepository;
  private final CategoryRepository categoryRepository;

  public Set<ShopCategory> saveShopCategory(Shop shop, Set<UUID> categoryIdSet) {

    // categoryIdSet 으로 categorySet 생성 및 확인
    Set<Category> categorySet = new HashSet<>(categoryRepository.findAllById(categoryIdSet));
    if (categorySet.isEmpty()) {
      throw new ApiException("존재하지 않는 카테고리 ID입니다.", HttpStatus.BAD_REQUEST);
    }

    // shopCategory 생성 및 반환
    return categorySet.stream()
        .map(category -> {
          ShopCategory shopCategory = new ShopCategory(shop, category);
          shopCategoryRepository.save(shopCategory);
          return shopCategory;
        })
        .collect(Collectors.toSet());
  }

  public Set<String> convertShopCategorySetToCategoryNameSet(Set<ShopCategory> shopCategorySet) {

    return shopCategorySet.stream()
        .map(shopCategory -> shopCategory.getCategory().getCategoryName())
        .collect(Collectors.toSet());
  }

  public Set<String> getCategoryNameSet(UUID shopId) {

    Set<ShopCategory> shopCategorySet = shopCategoryRepository.findAllByShopId(shopId);

    return shopCategorySet.stream()
        .map(shopCategory -> shopCategory.getCategory().getCategoryName())
        .collect(Collectors.toSet());
  }

}