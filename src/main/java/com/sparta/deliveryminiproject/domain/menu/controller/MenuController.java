package com.sparta.deliveryminiproject.domain.menu.controller;

import com.sparta.deliveryminiproject.domain.menu.dto.MenuRequestDto;
import com.sparta.deliveryminiproject.domain.menu.dto.MenuResponseDto;
import com.sparta.deliveryminiproject.domain.menu.service.MenuService;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum.Authority;
import com.sparta.deliveryminiproject.global.security.UserDetailsImpl;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("/{menuId}")
  public ResponseEntity getMenu(
      @PathVariable UUID menuId,
      @PathVariable UUID shopId) {

    MenuResponseDto menuResponseDto = menuService.getMenu(menuId, shopId);

    return ResponseEntity.ok(menuResponseDto);
  }

  @GetMapping
  public ResponseEntity<Page<MenuResponseDto>> getMenuList(
      @PathVariable UUID shopId,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam String searchQuery,
      @RequestParam(defaultValue = "createdAt") String sortBy,
      @RequestParam(defaultValue = "DESC") Direction direction,
      Pageable pageable) {

    Page<MenuResponseDto> pagedMenuDtoList = menuService.getMenuList(shopId, size, searchQuery,
        sortBy, direction,
        pageable);

    return ResponseEntity.ok(pagedMenuDtoList);
  }
}
