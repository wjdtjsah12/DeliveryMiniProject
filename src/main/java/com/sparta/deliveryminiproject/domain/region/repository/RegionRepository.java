package com.sparta.deliveryminiproject.domain.region.repository;

import com.sparta.deliveryminiproject.domain.region.entity.Region;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

public interface RegionRepository extends JpaRepository<Region, UUID> {

  default Region findRegionByIdOrElseThrow(UUID id) {
    return findById(id).orElseThrow(
        () -> new ApiException("존재하지 않는 지역 ID 입니다.", HttpStatus.NOT_FOUND)
    );
  }

}
