package com.sparta.deliveryminiproject.domain.gemini.repository;

import com.sparta.deliveryminiproject.domain.gemini.Entity.Gemini;
import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeminiRepository extends JpaRepository<Gemini, UUID> {

  List<Gemini> findAllByMenu(Menu menu);
}
