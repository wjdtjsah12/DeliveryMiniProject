package com.sparta.deliveryminiproject.domain.user.controller;

import com.sparta.deliveryminiproject.domain.user.dto.SigninRequestDto;
import com.sparta.deliveryminiproject.domain.user.dto.SignupRequestDto;
import com.sparta.deliveryminiproject.domain.user.service.UserService;
import com.sparta.deliveryminiproject.global.jwt.JwtUtil;
import com.sparta.deliveryminiproject.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
  private final JwtUtil jwtUtil;

  @PostMapping("/signup")
  public void signup(@Valid SignupRequestDto requestDto) {
    userService.signup(requestDto);
  }

  @PostMapping("/signin")
  public void signin(SigninRequestDto requestDto, HttpServletResponse response) {
    userService.signin(requestDto, response);
  }


  @PostMapping("/role/{role}")
  public void updateRole(@PathVariable String role,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    userService.updateRole(userDetails.getUser(), role);
  }

  @GetMapping("/info")
  public void users(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    System.out.println(
        "current signed user & role : " + userDetails.getUsername() + userDetails.getAuthorities());
  }
}
