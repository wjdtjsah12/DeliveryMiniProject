package com.sparta.deliveryminiproject.domain.region.controller;

import com.sparta.deliveryminiproject.domain.region.dto.RegionRequestDto;
import com.sparta.deliveryminiproject.domain.region.dto.RegionResponseDto;
import com.sparta.deliveryminiproject.domain.region.service.RegionService;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum.Authority;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

  private final RegionService regionService;

  public RegionController(RegionService regionService) {
    this.regionService = regionService;
  }

  // 지역 등록
  @Secured({Authority.MANAGER, Authority.MASTER,})
  @PostMapping
  public void createRegion(@Valid RegionRequestDto requestDto) {
    regionService.createRegion(requestDto);
  }

  // 지역 수정
  @Secured({Authority.MANAGER, Authority.MASTER,})
  @PutMapping("/{id}")
  public void updateRegion(@PathVariable UUID id, @Valid RegionRequestDto requestDto) {
    regionService.updateRegion(id, requestDto);
  }

  // 지역 키워드 검색
  @GetMapping
  public Page<RegionResponseDto> getRegionByKeywordAndIsDeleted(
      @RequestParam(required = false) String searchQuery,
      @RequestParam(defaultValue = "false") Boolean isDeleted,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "CreatedAt") String sortBy,
      @RequestParam(defaultValue = "DESC") String direction) {
    return regionService.getRegionByKeywordAndIsDeleted(
        searchQuery, isDeleted, page, size, sortBy, direction);
  }

  // 지역 정보 보기
  @Secured({Authority.OWNER, Authority.MANAGER, Authority.MASTER,})
  @GetMapping("/info/{id}")
  public RegionResponseDto getRegionInfoById(@PathVariable UUID id) {
    return regionService.getRegionInfoById(id);
  }

  // 지역 삭제
  @Secured({Authority.MANAGER, Authority.MASTER,})
  @DeleteMapping("/{id}")
  public void deleteRegion(@PathVariable UUID id) {
    regionService.deleteRegion(id);
  }
}
