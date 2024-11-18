package com.sparta.deliveryminiproject.domain.category.dto;

import com.sparta.deliveryminiproject.domain.category.entity.Category;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CategoryResponseDto {

  private UUID id;
  private String categoryName;
  private LocalDateTime createdAt;
  private String createdBy;
  private LocalDateTime updatedAt;
  private String updatedBy;
  private Boolean isDeleted;

  public CategoryResponseDto(Category category) {
    this.id = category.getId();
    this.categoryName = category.getCategoryName();
    this.createdAt = category.getCreatedAt();
    this.createdBy = category.getCreatedBy();
    this.updatedAt = category.getUpdatedAt();
    this.updatedBy = category.getUpdatedBy();
    this.isDeleted = category.getIsDeleted();
  }
}
