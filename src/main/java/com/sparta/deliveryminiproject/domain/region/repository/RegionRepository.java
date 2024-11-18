package com.sparta.deliveryminiproject.domain.region.repository;

import com.sparta.deliveryminiproject.domain.region.entity.Region;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, UUID> {

  default Region findRegionByIdOrElseThrow(UUID id) {
    return null;
  }

  Optional<Object> findByRegionName(String regionName);

  Page<Region> findAllByRegionNameContainingAndIsDeleted(String searchQuery, Boolean isDeleted,
      Pageable pageable);

  Page<Region> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);
}
