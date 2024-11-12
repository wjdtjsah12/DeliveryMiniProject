package com.sparta.deliveryminiproject.domain.shop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopRequestDto {

  private String shopName;

  private Long userId;

  private String address;

  @Size(max = 50, message = "가게 설명은 50자 이하로 작성해주세요.")
  private String description;

  private Integer minDeliveryPrice;

  private Integer deliveryTip;

  private Boolean isHidden = false;
}
