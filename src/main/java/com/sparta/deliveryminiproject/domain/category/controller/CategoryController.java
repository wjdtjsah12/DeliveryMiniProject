package com.sparta.deliveryminiproject.domain.category.controller;


import com.sparta.deliveryminiproject.domain.category.dto.CategoryRequestDto;
import com.sparta.deliveryminiproject.domain.category.dto.CategoryResponseDto;
import com.sparta.deliveryminiproject.domain.category.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categorys")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  // 카테고리 등록
  @PostMapping("")
  public void createCategory(@Valid CategoryRequestDto requestDto) {
    categoryService.createCategory(requestDto);
  }

  // 카테고리 수정
  @PutMapping("/{id}")
  public void updateCategory(@PathVariable UUID id, @Valid CategoryRequestDto requestDto) {
    categoryService.updateCategory(id, requestDto);
  }

  // 카테고리 키워드 검색
  @GetMapping("")
  public List<CategoryResponseDto> getCategoryByKeyword(
      @RequestParam(required = false) String searchQuery) {
    return categoryService.getCategoryByKeyword(searchQuery);
  }

  // 카테고리 삭제
  @DeleteMapping("/{id}")
  public void deleteCategory(@PathVariable UUID id) {
    categoryService.deleteCategory(id);
  }
}
