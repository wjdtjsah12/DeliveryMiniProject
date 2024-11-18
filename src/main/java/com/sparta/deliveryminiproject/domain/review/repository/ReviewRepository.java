package com.sparta.deliveryminiproject.domain.review.repository;

import com.sparta.deliveryminiproject.domain.review.entity.Review;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

public interface ReviewRepository extends JpaRepository<Review, UUID>, ReviewRepositoryCustom {

  default Review findReviewByIdOrElseThrow(UUID id) {
    return findById(id).orElseThrow(
        () -> new ApiException("존재하지 않는 리뷰 ID 입니다.", HttpStatus.NOT_FOUND)
    );
  }

}
