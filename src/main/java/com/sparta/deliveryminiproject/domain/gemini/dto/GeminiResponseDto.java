package com.sparta.deliveryminiproject.domain.gemini.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GeminiResponseDto {

  private List<Candidate> candidates;

  @Data
  public static class Candidate {

    private Content content;
    private String finishReason;
  }

  @Data
  public static class Content {

    private List<Parts> parts;
    private String role;

  }

  @Getter
  public static class Parts {

    private String text;
    private List<String> seperatedText;

    public void setText(String text) {
      this.text = text;
      List<String> speratedText = new ArrayList<>(
          Arrays.stream(text.replace("**", "").split("\\n")).toList());
      speratedText.removeAll(Arrays.asList("", null));
      this.seperatedText = speratedText;
    }
  }
}