package com.sparta.deliveryminiproject.domain.menu.repository;

import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, UUID> {

}
