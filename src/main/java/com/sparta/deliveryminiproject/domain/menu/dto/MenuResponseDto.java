package com.sparta.deliveryminiproject.domain.menu.dto;

import com.sparta.deliveryminiproject.domain.menu.entity.Menu;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponseDto {

  private UUID menuId;

  private String menuName;

  private String description;

  private Integer price;

  private Boolean isHidden;

  private UUID shopId;

  public MenuResponseDto(Menu menu) {
    this.menuId = menu.getId();
    this.menuName = menu.getMenuName();
    this.description = menu.getDescription();
    this.price = menu.getPrice();
    this.isHidden = menu.getIsHidden();
    this.shopId = menu.getShop().getId();
  }
}
