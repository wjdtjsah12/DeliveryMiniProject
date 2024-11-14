package com.sparta.deliveryminiproject.domain.menu.controller;

import com.sparta.deliveryminiproject.domain.menu.dto.MenuRequestDto;
import com.sparta.deliveryminiproject.domain.menu.dto.MenuResponseDto;
import com.sparta.deliveryminiproject.domain.menu.service.MenuService;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum.Authority;
import com.sparta.deliveryminiproject.global.security.UserDetailsImpl;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shops/{shopId}/menus")
public class MenuController {

  private final MenuService menuService;

  @Secured({Authority.OWNER, Authority.MANAGER, Authority.MASTER})
  @PostMapping
  public ResponseEntity createMenu(
      @ModelAttribute MenuRequestDto menuRequestDto,
      @PathVariable UUID shopId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {

    MenuResponseDto menuResponseDto = menuService.createMenu(
        menuRequestDto, shopId, userDetails.getUser());

    return ResponseEntity.ok(menuResponseDto);
  }
}
