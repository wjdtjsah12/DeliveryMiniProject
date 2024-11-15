package com.sparta.deliveryminiproject.domain.user.service;

import com.sparta.deliveryminiproject.domain.user.dto.SigninRequestDto;
import com.sparta.deliveryminiproject.domain.user.dto.SignupRequestDto;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum;
import com.sparta.deliveryminiproject.domain.user.repository.UserRepository;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import com.sparta.deliveryminiproject.global.jwt.JwtUtil;
import com.sparta.deliveryminiproject.global.security.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RedisUtil redisUtil;
  private final JwtUtil jwtUtil;

  public void signup(@Valid SignupRequestDto requestDto) {

    String username = requestDto.getUsername();
    String password = passwordEncoder.encode(requestDto.getPassword());

    Optional<User> checkUsername = userRepository.findByUsername(username);
    if (checkUsername.isPresent()) {
      throw new ApiException("Duplicate Username Found", HttpStatus.BAD_REQUEST);
    }

    User user = new User(username, password, UserRoleEnum.CUSTOMER);
    userRepository.save(user);
  }

  public void signin(SigninRequestDto requestDto, HttpServletResponse response) {

    String username = requestDto.getUsername();
    String password = requestDto.getPassword();

    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new IllegalArgumentException("Invalid Username")
    );

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new ApiException("Invalid Password", HttpStatus.BAD_REQUEST);
    }

    String token = jwtUtil.createToken(username, user.getRole());
    jwtUtil.addJwtToCookie(token, response);
  }

  public void signout(User user, HttpServletRequest request, HttpServletResponse response) {

    userRepository.findByUsername(user.getUsername())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    String token = jwtUtil.getTokenFromRequest(request);
    Long expSec = jwtUtil.getUserInfoFromToken(jwtUtil.substringToken(token)).getExpiration()
        .getTime();
    Long nowSec = new Date().getTime();

    redisUtil.addToBlackList(token, expSec - nowSec);
    jwtUtil.deleteJwtToCookie(request, response);
  }

  @Transactional
  public void updateRole(User user, String role) {
    User savedUser = userRepository.findByUsername(user.getUsername())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
    UserRoleEnum roleEnum = UserRoleEnum.contains(role);

    if (roleEnum == null) {
      throw new ApiException("Not Valid Role", HttpStatus.BAD_REQUEST);
    }

    savedUser.setRole(roleEnum);
  }
}
