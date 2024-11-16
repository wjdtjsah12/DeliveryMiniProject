package com.sparta.deliveryminiproject.domain.gemini.utility;


import com.sparta.deliveryminiproject.domain.gemini.dto.GeminiRequestDto;
import com.sparta.deliveryminiproject.domain.gemini.dto.GeminiResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class GeminiUtil {

  private final RestTemplate restTemplate;

  public GeminiUtil(@Qualifier("geminiRestTemplate") RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Value("${gemini.api.url}")
  private String apiUrl;

  @Value("${gemini.api.key}")
  private String apiKey;

  public ResponseEntity requestGeminiResponse(GeminiRequestDto requestDto) {
    try {
      String requestUrl = apiUrl + "?key=" + apiKey;
      GeminiResponseDto responseDto = restTemplate.postForObject(
          requestUrl,
          requestDto,
          GeminiResponseDto.class);

      return ResponseEntity.ok().body(responseDto);
    } catch (HttpClientErrorException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
