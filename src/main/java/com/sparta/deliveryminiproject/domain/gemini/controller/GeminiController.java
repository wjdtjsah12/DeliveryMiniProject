package com.sparta.deliveryminiproject.domain.gemini.controller;

import com.sparta.deliveryminiproject.domain.gemini.dto.GeminiRequestDto;
import com.sparta.deliveryminiproject.domain.gemini.utility.GeminiUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

  private final GeminiUtil geminiUtil;

  public GeminiController(GeminiUtil geminiUtil) {
    this.geminiUtil = geminiUtil;
  }

  @PostMapping
  public ResponseEntity<?> requestGeminiResponse(GeminiRequestDto requestDto) {
    return geminiUtil.requestGeminiResponse(requestDto);
  }
}
