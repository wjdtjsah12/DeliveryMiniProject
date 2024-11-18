package com.sparta.deliveryminiproject.global.security;

import com.sparta.deliveryminiproject.global.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtil {

  private final RedisTemplate<String, String> redisTemplate;

  public void addToBlackList(String token, Long expire) {
    redisTemplate.opsForValue().set(token, "blacklisted", expire);
  }

  public boolean isTokenBlacklisted(String token) {
    return redisTemplate.hasKey(token);
  }

}
