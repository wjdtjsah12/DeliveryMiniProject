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
      throw new IllegalArgumentException("Duplicate username found");
    }

    // TODO: 권한
    User user = new User(username, password, UserRoleEnum.MANAGER);
    userRepository.save(user);
  }
}
