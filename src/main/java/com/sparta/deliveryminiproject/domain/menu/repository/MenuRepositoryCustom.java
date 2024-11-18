package com.sparta.deliveryminiproject.domain.menu.repository;

import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuRepositoryCustom {

  Page<Menu> searchMenu(String searchQuery, Pageable pageable, UUID shopId);

}
