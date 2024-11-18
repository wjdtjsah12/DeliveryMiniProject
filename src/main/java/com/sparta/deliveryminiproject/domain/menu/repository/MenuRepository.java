package com.sparta.deliveryminiproject.domain.menu.repository;

import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

public interface MenuRepository extends JpaRepository<Menu, UUID>, MenuRepositoryCustom {

  default Menu findMenuByIdOrElseThrow(UUID id) {
    return findById(id).orElseThrow(
        () -> new ApiException("존재하지 않는 메뉴 ID 입니다.", HttpStatus.NOT_FOUND)
    );
  }

}
