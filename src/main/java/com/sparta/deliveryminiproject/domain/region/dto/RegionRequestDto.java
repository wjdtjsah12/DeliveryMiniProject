package com.sparta.deliveryminiproject.domain.region.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegionRequestDto {

  // 한/영 + 글자수 제한 정규표현식
  @Pattern(regexp = "^[a-zA-Z가-힣]{1,10}$", message = "1 ~ 10자 사이의 한글과 영대소문자만 입력 가능합니다.")
  private String regionName;

}
