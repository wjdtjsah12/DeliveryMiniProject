package com.sparta.deliveryminiproject.domain.user.service;

import com.sparta.deliveryminiproject.domain.user.dto.SigninRequestDto;
import com.sparta.deliveryminiproject.domain.user.dto.SignupRequestDto;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import com.sparta.deliveryminiproject.domain.user.entity.UserRoleEnum;
import com.sparta.deliveryminiproject.domain.user.repository.UserRepository;
import com.sparta.deliveryminiproject.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public void signup(SignupRequestDto requestDto) {

    String username = requestDto.getUsername();
    String password = passwordEncoder.encode(requestDto.getPassword());

    Optional<User> checkUsername = userRepository.findByUsername(username);
    if (checkUsername.isPresent()) {
      throw new IllegalArgumentException("Duplicate Username Found");
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
      throw new IllegalArgumentException("Invalid Password");
    }

    String token = jwtUtil.createToken(username, user.getRole());
    jwtUtil.addJwtToCookie(token, response);
  }

  @Transactional
  public void updateRole(User user, UserRoleEnum role) {
    User savedUser = userRepository.findByUsername(user.getUsername())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    savedUser.setRole(role);
  }
}
