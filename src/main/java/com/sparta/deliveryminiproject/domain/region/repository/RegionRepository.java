package com.sparta.deliveryminiproject.domain.region.repository;

import com.sparta.deliveryminiproject.domain.region.entity.Region;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

public interface RegionRepository extends JpaRepository<Region, UUID> {

  // 컴파일 에러 패스용 더미
  default Region findRegionByIdOrElseThrow(UUID id) {
    return null;
  }

  Optional<Object> findByRegionName(String regionName);

  Page<Region> findAllByRegionNameContainingAndIsDeleted(String searchQuery, Boolean isDeleted,
      Pageable pageable);

  Page<Region> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);
}
