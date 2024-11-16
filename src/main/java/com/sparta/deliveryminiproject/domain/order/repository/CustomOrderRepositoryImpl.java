package com.sparta.deliveryminiproject.domain.order.repository;

import static com.sparta.deliveryminiproject.domain.cart.entity.QCart.cart;
import static com.sparta.deliveryminiproject.domain.menu.entity.QMenu.menu;
import static com.sparta.deliveryminiproject.domain.order.entity.QOrder.order;
import static com.sparta.deliveryminiproject.domain.shop.entity.QShop.shop;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.deliveryminiproject.domain.order.dto.OrderResponseDto;
import com.sparta.deliveryminiproject.domain.order.dto.OrderSearchCondition;
import com.sparta.deliveryminiproject.domain.order.dto.QOrderResponseDto;
import com.sparta.deliveryminiproject.domain.order.entity.OrderStatus;
import com.sparta.deliveryminiproject.domain.order.entity.OrderType;
import com.sparta.deliveryminiproject.domain.order.entity.QOrder;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class CustomOrderRepositoryImpl implements CustomOrderRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Page<OrderResponseDto> getOrders(User user, Pageable pageable,
      String searchQuery, OrderSearchCondition condition) {
    JPAQuery<OrderResponseDto> query = queryFactory
        .selectDistinct(new QOrderResponseDto(
            order.id,
            order.createdAt,
            order.orderStatus,
            shop.shopName,
            order.totalPrice
        ))
        .from(order)
        .join(order.cartList, cart)
        .join(cart.shop, shop);

    JPAQuery<Long> countQuery = queryFactory
        .select(order.countDistinct())
        .from(order)
        .join(order.cartList, cart)
        .join(cart.shop, shop);

    if (searchQuery != null) {
      query.join(cart.menu, menu);
      countQuery.join(cart.menu, menu);
    }

    query.where(
        order.user.eq(user),
        order.isDeleted.eq(false),
        containsShopOrMenuName(searchQuery) // 변경된 부분
    );

    countQuery.where(
        order.user.eq(user),
        order.isDeleted.eq(false),
        containsShopOrMenuName(searchQuery) // 변경된 부분
    );

    if (condition != null) {
      query.where(
          orderTypeEq(condition.getOrderType()),
          orderStatusEq(condition.getOrderStatus()),
          createdAtBetween(condition.getStartAt(), condition.getEndAt())
      );
      countQuery.where(
          orderTypeEq(condition.getOrderType()),
          orderStatusEq(condition.getOrderStatus()),
          createdAtBetween(condition.getStartAt(), condition.getEndAt())
      );
    }

    List<OrderResponseDto> content = query.
        orderBy(getOrderSpecifier(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private BooleanExpression containsShopOrMenuName(String searchQuery) {
    if (searchQuery == null) {
      return null;
    }
    return shop.shopName.containsIgnoreCase(searchQuery)
        .or(menu.menuName.containsIgnoreCase(searchQuery));
  }

  private BooleanExpression orderTypeEq(OrderType orderType) {
    return orderType == null ? null : order.orderType.eq(orderType);
  }

  private BooleanExpression orderStatusEq(OrderStatus orderStatus) {
    return orderStatus == null ? null : order.orderStatus.eq(orderStatus);
  }

  private BooleanExpression createdAtBetween(LocalDate startAt, LocalDate endAt) {
    return startAt == null && endAt == null ? null
        : order.createdAt.between(startAt.atStartOfDay(), endAt.atTime(
            LocalTime.MAX));
  }

  private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
    List<OrderSpecifier> orders = new ArrayList<>();

    sort.stream().forEach(order -> {
      Order direction = order.isAscending() ? Order.ASC : Order.DESC;
      PathBuilder pathBuilder = new PathBuilder(QOrder.order.getType(), QOrder.order.getMetadata());
      String prop = order.getProperty();
      orders.add(new OrderSpecifier(direction, pathBuilder.get(prop)));
    });
    return orders;
  }
}
