package com.sparta.deliveryminiproject.domain.gemini.utility;


import com.sparta.deliveryminiproject.domain.gemini.Entity.Gemini;
import com.sparta.deliveryminiproject.domain.gemini.dto.GeminiRequestDto;
import com.sparta.deliveryminiproject.domain.gemini.dto.GeminiResponseDto;
import com.sparta.deliveryminiproject.domain.gemini.repository.GeminiRepository;
import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import com.sparta.deliveryminiproject.global.exception.ApiException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class GeminiUtil {

  private final RestTemplate restTemplate;
  private final GeminiRepository geminiRepository;

  public GeminiUtil(@Qualifier("geminiRestTemplate") RestTemplate restTemplate,
      GeminiRepository geminiRepository) {
    this.restTemplate = restTemplate;
    this.geminiRepository = geminiRepository;
  }

  @Value("${gemini.api.url}")
  private String apiUrl;

  @Value("${gemini.api.key}")
  private String apiKey;

  @Transactional
  public List<Gemini> getGeminiResponses(Menu menu) {
    List<Gemini> geminiList = checkGeminiReponseExist(menu);
    // DB 내부 API Response 찾기
    if (!geminiList.isEmpty()) {
      // DB 내부 Data 있으면 사용
      return geminiList;
    } else {
      // DB 내부 Data 없을 시 API 응답 새로 요청
      try {
        String requestUrl = apiUrl + "?key=" + apiKey;
        GeminiResponseDto responseDto = restTemplate.postForObject(
            requestUrl,
            new GeminiRequestDto(menu),
            GeminiResponseDto.class);

        if (responseDto != null) {
          List<String> seperatedTexts = responseDto.getSeperatedTexts();
          if (seperatedTexts != null) {
            for (String seperatedText : seperatedTexts) {
              Gemini gemini = new Gemini(menu, seperatedText);
              geminiList.add(gemini);
              geminiRepository.save(gemini);
            }
          } else {
            throw new ApiException("Response의 SeperatedTexts가 없습니다.", HttpStatus.NOT_FOUND);
          }
        } else {
          throw new ApiException("API Request에 대한 Response가 없습니다.", HttpStatus.NOT_FOUND);
        }

        return geminiList;
      } catch (HttpClientErrorException e) {
        throw new ApiException(e.getMessage(), HttpStatus.valueOf(e.getStatusCode().toString()));
      }
    }
  }

  private List<Gemini> checkGeminiReponseExist(Menu menu) {
    List<Gemini> geminiList = geminiRepository.findAllByMenu(menu);
    if (!geminiList.isEmpty()) {
      return geminiList;
    }

    return new ArrayList<>();
  }
}
