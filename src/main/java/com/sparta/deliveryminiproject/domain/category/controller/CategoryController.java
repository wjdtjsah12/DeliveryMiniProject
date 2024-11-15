package com.sparta.deliveryminiproject.domain.category.controller;


import com.sparta.deliveryminiproject.domain.category.dto.CategoryRequestDto;
import com.sparta.deliveryminiproject.domain.category.dto.CategoryResponseDto;
import com.sparta.deliveryminiproject.domain.category.service.CategoryService;
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
@RequestMapping("/api/categorys")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  // 카테고리 등록
  @Secured({Authority.MANAGER, Authority.MASTER,})
  @PostMapping
  public void createCategory(@Valid CategoryRequestDto requestDto) {
    categoryService.createCategory(requestDto);
  }

  // 카테고리 수정
  @Secured({Authority.MANAGER, Authority.MASTER,})
  @PutMapping("/{id}")
  public void updateCategory(@PathVariable UUID id, @Valid CategoryRequestDto requestDto) {
    categoryService.updateCategory(id, requestDto);
  }

  // 카테고리 키워드 검색
  @GetMapping
  public Page<CategoryResponseDto> getCategoryByKeywordAndIsDeleted(
      @RequestParam(required = false) String searchQuery,
      @RequestParam(defaultValue = "false") Boolean isDeleted,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "CreatedAt") String sortBy,
      @RequestParam(defaultValue = "DESC") String direction) {
    return categoryService.getCategoryByKeywordAndIsDeleted(
        searchQuery, isDeleted, page, size, sortBy, direction);
  }

  // 카테고리 정보 보기
  @Secured({Authority.OWNER, Authority.MANAGER, Authority.MASTER,})
  @GetMapping("/info/{id}")
  public CategoryResponseDto getCategoryInfoById(@PathVariable UUID id) {
    return categoryService.getCategoryInfoById(id);
  }

  // 카테고리 삭제
  @Secured({Authority.MANAGER, Authority.MASTER,})
  @DeleteMapping("/{id}")
  public void deleteCategory(@PathVariable UUID id) {
    categoryService.deleteCategory(id);
  }
}
