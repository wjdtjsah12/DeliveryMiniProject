package com.sparta.deliveryminiproject.domain.gemini.dto;

import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class GeminiRequestDto {

  private List<Content> contents;

  @Data
  public class Content {

    private List<Part> parts;

    public Content(String text) {
      parts = new ArrayList<>();
      Part part = new Part(text);
      parts.add(part);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Part {

      private String text;
    }
  }

  public GeminiRequestDto(Menu menu) {
    this.contents = new ArrayList<>();
    Content content = new Content(
        menu.getMenuName() + "에 대한 대해 각각 다른 3가지 설명을 해줘. 설명 별로 간결하게 50자 내외로 작성해줘");
    contents.add(content);
  }
}