package com.sparta.deliveryminiproject.domain.cart.controller;

import com.sparta.deliveryminiproject.domain.cart.dto.CartRequestDto;
import com.sparta.deliveryminiproject.domain.cart.entity.Cart;
import com.sparta.deliveryminiproject.domain.cart.service.CartService;
import com.sparta.deliveryminiproject.global.response.ApiResponse;
import com.sparta.deliveryminiproject.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

  private final CartService cartService;

  @PostMapping
  public ResponseEntity addMenu(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody @Valid CartRequestDto cartRequestDto
  ) {
    Cart cart = cartService.addMenuToCart(userDetails.getUser(), cartRequestDto);

    URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/api/carts")
        .path("/{id}")
        .buildAndExpand(cart.getId())
        .toUri();

    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity getCart(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{cartId}")
  public ResponseEntity updateQuantity(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable UUID cartId,
      @RequestParam String operator
  ) {
    cartService.updateQuantity(userDetails.getUser(), cartId, operator);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{cartId}")
  public ResponseEntity deleteCart(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable UUID cartId
  ) {
    cartService.deleteMenuAtCart(userDetails.getUser(), cartId);
    return ResponseEntity.ok().build();
  }
}
