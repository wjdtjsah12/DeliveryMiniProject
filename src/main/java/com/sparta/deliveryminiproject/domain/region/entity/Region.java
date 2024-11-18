package com.sparta.deliveryminiproject.domain.region.entity;

import com.sparta.deliveryminiproject.domain.region.dto.RegionRequestDto;
import com.sparta.deliveryminiproject.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity(name = "p_region")
@NoArgsConstructor
public class Region extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "region_id", unique = true, nullable = false)
  private UUID id;

  @Setter
  @Column(nullable = false)
  private String regionName;

  public Region(RegionRequestDto requestDto) {
    this.regionName = requestDto.getRegionName();
  }
}
