package com.sparta.deliveryminiproject.domain.shop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.deliveryminiproject.domain.region.entity.QRegion;
import com.sparta.deliveryminiproject.domain.shop.entity.QShop;
import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ShopRepositoryImpl implements ShopRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public ShopRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Page<Shop> searchShops(
      String searchQuery, Pageable pageable) {

    QShop shop = QShop.shop;
    QRegion region = QRegion.region;

    // 조건 설정(where절에 해당)
    BooleanExpression searchCondition = shop.shopName.containsIgnoreCase(searchQuery)
        .and(shop.isDeleted.isFalse())
        .and(shop.isHidden.isFalse());

    // 쿼리 실행
    List<Shop> result = queryFactory
        .selectFrom(shop)
        .join(shop.region, region).fetchJoin() // fetchJoin 추가 (region과의 N+1 문제 해결)
        .where(searchCondition)
        .offset(pageable.getOffset())  // 조회할 데이터의 시작 위치 지정
        .limit(pageable.getPageSize())  // 한 번에 조회할 데이터의 개수 지정
        .fetch();

    // 총 개수 (페이지네이션을 위한 total count)
    long total = queryFactory
        .selectFrom(shop)
        .where(searchCondition)
        .fetchCount();

    // 결과와 total count를 이용해 Page 객체를 생성하여 반환
    return new PageImpl<>(result, pageable, total);

  }
}
