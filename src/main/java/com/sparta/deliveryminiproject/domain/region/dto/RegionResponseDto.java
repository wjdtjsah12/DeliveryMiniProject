package com.sparta.deliveryminiproject.domain.region.dto;

import com.sparta.deliveryminiproject.domain.region.entity.Region;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class RegionResponseDto {

  private UUID id;
  private String regionName;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
  private Boolean isDeleted;

  public RegionResponseDto(Region region) {
    this.id = region.getId();
    this.regionName = region.getRegionName();
    this.createdAt = region.getCreatedAt();
    this.createdBy = region.getCreatedBy();
    this.updatedAt = region.getUpdatedAt();
    this.updatedBy = region.getUpdatedBy();
    this.isDeleted = region.getIsDeleted();
  }
}

