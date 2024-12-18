package com.sparta.deliveryminiproject.domain.menu.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import com.sparta.deliveryminiproject.domain.menu.entity.QMenu;
import com.sparta.deliveryminiproject.global.sort.DynamicSortUtil;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class MenuRepositoryImpl implements MenuRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public MenuRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }


  @Override
  public Page<Menu> searchMenu(
      String searchQuery, Pageable pageable, UUID shopId) {

    QMenu menu = QMenu.menu;

    BooleanExpression searchCondition = menu.menuName.containsIgnoreCase(searchQuery)
        .and(menu.isDeleted.isFalse())
        .and(menu.isHidden.isFalse());

    List<Menu> result = queryFactory
        .selectFrom(menu)
        .where(searchCondition)
        .where(menu.shop.id.eq(shopId))
        .orderBy(
            DynamicSortUtil.getDynamicSort(
                    pageable.getSort(), menu.getType(), menu.getMetadata())
                .toArray(OrderSpecifier[]::new))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = queryFactory
        .select(menu.count())
        .from(menu)
        .where(searchCondition)
        .where(menu.shop.id.eq(shopId))
        .fetchOne();

    if (total == null) {
      total = 0L;
    }

    return new PageImpl<>(result, pageable, total);

  }
}
