package com.sparta.deliveryminiproject.domain.region.service;

import com.sparta.deliveryminiproject.domain.category.entity.Category;
import com.sparta.deliveryminiproject.domain.region.dto.RegionRequestDto;
import com.sparta.deliveryminiproject.domain.region.dto.RegionResponseDto;
import com.sparta.deliveryminiproject.domain.region.entity.Region;
import com.sparta.deliveryminiproject.domain.region.repository.RegionRepository;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegionService {

  private final RegionRepository regionRepository;

  public RegionService(RegionRepository regionRepository) {
    this.regionRepository = regionRepository;
  }

  public void createRegion(RegionRequestDto requestDto) {
    checkRegionExist(requestDto.getRegionName());

    regionRepository.save(new Region(requestDto));
  }

  // DirtyCheck
  @Transactional
  public void updateRegion(UUID id, RegionRequestDto requestDto) {
    checkRegionExist(requestDto.getRegionName());
    Region region = checkRegionExist(id);

    // 지역명 수정
    region.setRegionName(requestDto.getRegionName());
  }

  public Page<RegionResponseDto> getRegionByKeywordAndIsDeleted(String searchQuery,
      Boolean isDeleted, int page, int size, String sortBy, String direction) {
    // Pagination 설정
    size = size == 30 || size == 50 ? size : 10;
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortBy);

    if (searchQuery != null && !searchQuery.isEmpty()) {
      return regionRepository.findAllByRegionNameContainingAndIsDeleted(
              searchQuery, isDeleted, pageable)
          .map(RegionResponseDto::new);
    } else {
      return regionRepository.findAllByIsDeleted(isDeleted, pageable)
          .map(RegionResponseDto::new);
    }
  }

  public RegionResponseDto getRegionInfoById(UUID id) {
    Region region = checkRegionExist(id);
    return new RegionResponseDto(region);
  }

  @Transactional
  public void deleteRegion(UUID id) {
    Region region = checkRegionExist(id);
    region.setIsDeleted(true);
  }

  // DB내 지역 이름 중복 체크 - 오버라이딩
  private void checkRegionExist(String regionName) {
    if (regionRepository.findByRegionName(regionName).isPresent()) {
      throw new ApiException("이미 존재하는 지역명 입니다.", HttpStatus.BAD_REQUEST);
    }
  }

  // DB내 카테고리 Id 체크 - 오버라이딩

  private Region checkRegionExist(UUID id) {
    return regionRepository.findById(id).orElseThrow(() ->
        new ApiException("해당하는 카테고리가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
  }
}
