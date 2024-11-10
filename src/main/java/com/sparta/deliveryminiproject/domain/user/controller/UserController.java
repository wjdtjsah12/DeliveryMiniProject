package com.sparta.deliveryminiproject.domain.user.controller;

import com.sparta.deliveryminiproject.domain.user.dto.SigninRequestDto;
import com.sparta.deliveryminiproject.domain.user.dto.SignupRequestDto;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum;
import com.sparta.deliveryminiproject.domain.user.service.UserService;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import com.sparta.deliveryminiproject.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private AuthenticationManager authenticationManager;
  private final UserService userService;

  @PostMapping("/signup")
  public void signup(SignupRequestDto requestDto) {
    userService.signup(requestDto);
  }

  @GetMapping("/info")
  public void users(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    System.out.println(
        "current signed user & role : " + userDetails.getUsername() + userDetails.getAuthorities());
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public ResponseEntity<ApiException> handleException(IllegalArgumentException ex) {
    ApiException restApiException = new ApiException(ex.getMessage(), HttpStatus.BAD_REQUEST);
    return new ResponseEntity<>(
        restApiException,
        HttpStatus.BAD_REQUEST
    );
  }
}
