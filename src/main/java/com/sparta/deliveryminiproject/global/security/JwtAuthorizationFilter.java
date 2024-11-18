package com.sparta.deliveryminiproject.global.security;

import com.sparta.deliveryminiproject.global.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsServiceImpl userDetailsService;


  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
      FilterChain filterChain) throws ServletException, IOException {

    String path = req.getRequestURI();
    if (path.equals("/api/users/signup") || path.equals("/api/users/signin")) {
      filterChain.doFilter(req, res);
      return;
    }

    String tokenValue = jwtUtil.getTokenFromRequest(req);

    if (StringUtils.hasText(tokenValue)) {
      tokenValue = jwtUtil.substringToken(tokenValue);

      if (!jwtUtil.validateToken(tokenValue, res)) {
        return;
      }

      Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

      try {
        setAuthentication(info.getSubject());
      } catch (Exception e) {
        System.out.println(e.getMessage());
        return;
      }
    } else {
      sendErrorResponse(res, "Token is missing", HttpStatus.FORBIDDEN);
      return;
    }

    filterChain.doFilter(req, res);
  }

  public static void sendErrorResponse(HttpServletResponse res, String message, HttpStatus status)
      throws IOException {
    res.setStatus(status.value());
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    res.getWriter().write(String.format(
        "{\"message\": \"%s\", \"status\": %d}",
        message,
        status.value()
    ));
  }

  public void setAuthentication(String username) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    Authentication authentication = createAuthentication(username);
    context.setAuthentication(authentication);

    SecurityContextHolder.setContext(context);
  }

  private Authentication createAuthentication(String username) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }
}