package com.sparta.deliveryminiproject.domain.shop.dto;

import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopResponseDto {

  private String shopName;

  private Long userId;

  private String address;

  private String description;

  private Integer minDeliveryPrice;

  private Integer deliveryTip;

  private Boolean isHidden;

  private String regionName;

  private Set<String> categoryName;

  public ShopResponseDto(Shop shop, Set<String> categoryNameSet) {
    this.shopName = shop.getShopName();
    this.userId = shop.getUser().getId();
    this.address = shop.getAddress();
    this.description = shop.getDescription();
    this.minDeliveryPrice = shop.getMinDeliveryPrice();
    this.deliveryTip = shop.getDeliveryTip();
    this.isHidden = shop.getIsHidden();
    this.regionName = shop.getRegion().getRegionName();
    this.categoryName = categoryNameSet;
  }
}
