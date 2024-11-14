package com.sparta.deliveryminiproject.domain.category.entity;

import com.sparta.deliveryminiproject.domain.category.dto.CategoryRequestDto;
import com.sparta.deliveryminiproject.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity(name = "p_category")
@NoArgsConstructor
public class Category extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "category_id")
  private UUID id;

  @Setter
  @Column(nullable = false)
  private String categoryName;

  public Category(CategoryRequestDto dto) {
    this.categoryName = dto.getCategoryName();
  }
}
