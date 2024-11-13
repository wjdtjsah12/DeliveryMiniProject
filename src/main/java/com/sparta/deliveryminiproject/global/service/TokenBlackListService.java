package com.sparta.deliveryminiproject.global.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenBlackListService {

  private final RedisTemplate<String, String> redisTemplate;

  public void addToBlackList(String token) {
    redisTemplate.opsForValue().set(token, "blacklisted");
  }

  public boolean isTokenBlacklisted(String token) {
    return redisTemplate.hasKey(token);
  }
}
