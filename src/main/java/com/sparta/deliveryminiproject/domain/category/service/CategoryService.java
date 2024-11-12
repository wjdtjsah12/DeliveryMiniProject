package com.sparta.deliveryminiproject.domain.category.service;

import com.sparta.deliveryminiproject.domain.category.dto.CategoryRequestDto;
import com.sparta.deliveryminiproject.domain.category.dto.CategoryResponseDto;
import com.sparta.deliveryminiproject.domain.category.entity.Category;
import com.sparta.deliveryminiproject.domain.category.repository.CategoryRepository;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public void createCategory(CategoryRequestDto requestDto) {
    checkCategoryExist(requestDto.getCategoryName());

    // 카테고리 생성
    categoryRepository.save(new Category(requestDto));
  }

  // DirtyCheck
  @Transactional
  public void updateCategory(UUID id, @Valid CategoryRequestDto requestDto) {
    checkCategoryExist(requestDto.getCategoryName());

    Category category = checkCategoryExist(id);

    // 카테고리명 수정
    category.setCategoryName(requestDto.getCategoryName());
  }

  public List<CategoryResponseDto> getCategoryByKeyword(String searchQuery) {
    if (searchQuery != null && !searchQuery.isEmpty()) {
      return categoryRepository.findAllByCategoryNameContaining(searchQuery)
          .stream().map(CategoryResponseDto::new).toList();
    } else {
      return categoryRepository.findAll()
          .stream().map(CategoryResponseDto::new).toList();
    }
  }

  public void deleteCategory(UUID id) {
    checkCategoryExist(id);
    categoryRepository.deleteById(id);
  }

  // DB내 카테고리 이름 중복 체크 - 오버라이딩
  private void checkCategoryExist(String categoryName) {
    if (categoryRepository.findByCategoryName(categoryName).isPresent()) {
      throw new ApiException("이미 존재하는 카테고리명 입니다.", HttpStatus.BAD_REQUEST);
    }
  }

  // DB내 카테고리 Id 체크 - 오버라이딩
  private Category checkCategoryExist(UUID id) {
    return categoryRepository.findById(id).orElseThrow(() ->
        new ApiException("해당하는 카테고리가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
  }
}