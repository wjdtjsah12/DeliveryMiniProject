package com.sparta.deliveryminiproject.domain.review.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewRequestDto {

  @NotNull(message = "rating 값을 입력해주세요.")
  @Min(value = 1, message = "rating은 1 이상의 값을 입력해야 합니다.")
  @Max(value = 5, message = "rating은 5 이하의 값을 입력해야 합니다.")
  private Integer rating;

  private String content;

  private UUID menuId;
}
