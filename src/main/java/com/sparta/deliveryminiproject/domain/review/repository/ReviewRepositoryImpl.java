package com.sparta.deliveryminiproject.domain.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.deliveryminiproject.domain.review.entity.QReview;
import com.sparta.deliveryminiproject.domain.review.entity.Review;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public ReviewRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Page<Review> findByShopIdAndIsDeletedFalse(UUID shopId, Pageable pageable) {

    QReview review = QReview.review;

    List<Review> result = queryFactory
        .selectFrom(review)
        .where(review.isDeleted.isFalse())
        .where(review.shop.id.eq(shopId))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = queryFactory
        .select(review.count())
        .from(review)
        .where(review.isDeleted.isFalse())
        .where(review.shop.id.eq(shopId))
        .fetchOne();

    if (total == null) {
      total = 0L;
    }

    return new PageImpl<>(result, pageable, total);

  }
}
