package com.sparta.deliveryminiproject.domain.user.controller;

import com.sparta.deliveryminiproject.domain.user.dto.RoleResponseDto;
import com.sparta.deliveryminiproject.domain.user.dto.SigninRequestDto;
import com.sparta.deliveryminiproject.domain.user.dto.SigninResponseDto;
import com.sparta.deliveryminiproject.domain.user.dto.SignupRequestDto;
import com.sparta.deliveryminiproject.domain.user.service.UserService;
import com.sparta.deliveryminiproject.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public void signup(@Valid SignupRequestDto requestDto) {
    userService.signup(requestDto);
  }

  @PostMapping("/signin")
  public ResponseEntity signin(SigninRequestDto requestDto, HttpServletResponse response) {
    SigninResponseDto responseDto = userService.signin(requestDto, response);
    return ResponseEntity.ok(responseDto);
  }

  @PostMapping("/signout")
  public void signout(@AuthenticationPrincipal UserDetailsImpl userDetails,
      HttpServletRequest request, HttpServletResponse response) {
    userService.signout(userDetails.getUser(), request, response);
  }

  @PostMapping("/role/{role}")
  public ResponseEntity updateRole(@PathVariable String role,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    RoleResponseDto responseDto = userService.updateRole(userDetails.getUser(), role);
    return ResponseEntity.ok(responseDto);
  }
}
