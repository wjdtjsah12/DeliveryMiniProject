package com.sparta.deliveryminiproject.domain.category.repository;

import com.sparta.deliveryminiproject.domain.category.entity.Category;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

  Optional<Category> findByCategoryName(String categoryName);

  List<Category> findAllByCategoryNameContaining(String searchQuery);
}
