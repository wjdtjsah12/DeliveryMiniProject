package com.sparta.deliveryminiproject.domain.shop.entity;

import com.sparta.deliveryminiproject.domain.shop.dto.ShopRequestDto;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import com.sparta.deliveryminiproject.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "p_shop")
public class Shop extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "shop_id", unique = true, nullable = false)
  private UUID id;

  @Column(nullable = false)
  private String shopName;

  @Column(nullable = false)
  private String address;

  @Column
  private String description;

  @Column
  private Integer minDeliveryPrice;

  @Column
  private Integer deliveryTip;

  @Column(nullable = false)
  private Boolean isHidden = false;

  /**
   * 연관관계 맵핑
   */
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

//  private Set<Category> categories; //shop_category 에서 받아와서 주입? 꼭 갖고있어야하는 필드인가


  public Shop(ShopRequestDto shopRequestDto, User owner) {
    this.shopName = shopRequestDto.getShopName();
    this.address = shopRequestDto.getAddress();
    this.user = owner;
  }

}