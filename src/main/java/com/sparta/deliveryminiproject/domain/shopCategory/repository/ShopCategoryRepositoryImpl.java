package com.sparta.deliveryminiproject.domain.shopCategory.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.deliveryminiproject.domain.category.entity.QCategory;
import com.sparta.deliveryminiproject.domain.shopCategory.entity.QShopCategory;
import com.sparta.deliveryminiproject.domain.shopCategory.entity.ShopCategory;
import jakarta.persistence.EntityManager;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ShopCategoryRepositoryImpl implements ShopCategoryRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public ShopCategoryRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Set<ShopCategory> findAllByShopId(UUID shopId) {

    QShopCategory shopCategory = QShopCategory.shopCategory;
    QCategory category = QCategory.category;

    return queryFactory
        .selectFrom(shopCategory)
        .join(shopCategory.category, category).fetchJoin()
        .fetchJoin()
        .where(shopCategory.shop.id.eq(shopId))
        .fetch()
        .stream()
        .collect(Collectors.toSet());
  }
}
