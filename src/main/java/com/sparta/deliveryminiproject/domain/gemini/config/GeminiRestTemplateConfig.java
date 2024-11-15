package com.sparta.deliveryminiproject.domain.gemini.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GeminiRestTemplateConfig {

  @Bean(name = "geminiRestTemplate")
  public RestTemplate geminiRestTemplate() {
    return new RestTemplate();

  }
}
