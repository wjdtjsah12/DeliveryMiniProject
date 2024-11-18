package com.sparta.deliveryminiproject.domain.review.repository;

import com.sparta.deliveryminiproject.domain.review.entity.Review;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {

  Page<Review> findByShopIdAndIsDeletedFalse(UUID shopId, Pageable pageable);

}
