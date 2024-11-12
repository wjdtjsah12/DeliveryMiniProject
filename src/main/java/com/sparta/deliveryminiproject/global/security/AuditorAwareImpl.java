package com.sparta.deliveryminiproject.global.security;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    // Spring Security에서 현재 인증된 사용자 정보 가져오기

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(
        authentication.getPrincipal())) {
      return Optional.of("SYSTEM");
    }

    return Optional.of(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
  }
}