package com.sparta.deliveryminiproject.domain.category.repository;

import com.sparta.deliveryminiproject.domain.category.dto.CategoryResponseDto;
import com.sparta.deliveryminiproject.domain.category.entity.Category;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

  Optional<Category> findByCategoryName(String categoryName);

  Page<Category> findAllByCategoryNameContainingAndIsDeleted(String searchQuery, Boolean isDeleted, Pageable pageable);

  Page<Category> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);
}
