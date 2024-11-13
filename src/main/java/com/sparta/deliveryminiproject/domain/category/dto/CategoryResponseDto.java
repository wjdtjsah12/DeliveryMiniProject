package com.sparta.deliveryminiproject.domain.category.dto;

import com.sparta.deliveryminiproject.domain.category.entity.Category;
import java.util.UUID;
import lombok.Getter;

@Getter
public class CategoryResponseDto {

  private UUID id;
  private String categoryName;

  public CategoryResponseDto(Category category) {
    this.id = category.getId();
    this.categoryName = category.getCategoryName();
  }
}
