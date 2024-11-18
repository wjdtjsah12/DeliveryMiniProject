package com.sparta.deliveryminiproject.domain.shop.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.deliveryminiproject.domain.shop.entity.QShop;
import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import com.sparta.deliveryminiproject.global.sort.DynamicSortUtil;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ShopRepositoryImpl implements ShopRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public ShopRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Page<Shop> searchShopsByKeyword(
      String searchQuery, Pageable pageable) {

    QShop shop = QShop.shop;

    // 조건 설정(where절에 해당)
    BooleanExpression searchCondition = shop.shopName.containsIgnoreCase(searchQuery)
        .and(shop.isDeleted.isFalse())
        .and(shop.isHidden.isFalse());

    // 쿼리 실행
    return getShops(pageable, shop, searchCondition);

  }

  @Override
  public Page<Shop> findShopsByRegionId(UUID regionId, Pageable pageable) {

    QShop shop = QShop.shop;

    BooleanExpression searchCondition = shop.region.id.eq(regionId).and(shop.isDeleted.isFalse())
        .and(shop.isHidden.isFalse());

    return getShops(pageable, shop, searchCondition);
  }

  private Page<Shop> getShops(Pageable pageable, QShop shop, BooleanExpression searchCondition) {
    List<Shop> result = queryFactory
        .selectFrom(shop)
        .where(searchCondition)
        .orderBy(
            DynamicSortUtil.getDynamicSort(
                    pageable.getSort(), shop.getType(), shop.getMetadata())
                .toArray(OrderSpecifier[]::new))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = queryFactory
        .select(shop.count())
        .from(shop)
        .where(searchCondition)
        .fetchOne();

    if (total == null) {
      total = 0L;
    }

    return new PageImpl<>(result, pageable, total);
  }
}
