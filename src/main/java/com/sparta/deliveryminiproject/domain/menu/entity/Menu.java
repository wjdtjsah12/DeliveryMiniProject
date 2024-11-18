package com.sparta.deliveryminiproject.domain.menu.entity;

import com.sparta.deliveryminiproject.domain.menu.dto.MenuRequestDto;
import com.sparta.deliveryminiproject.domain.shop.entity.Shop;
import com.sparta.deliveryminiproject.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "p_menu")
@Getter
@NoArgsConstructor
public class Menu extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String menuName;

  @Setter
  private String description;

  private Integer price;

  @Column(nullable = false)
  private Boolean isHidden = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "shop_id", nullable = false)
  private Shop shop;


  public Menu(MenuRequestDto menuRequestDto, Shop shop) {
    this.menuName = menuRequestDto.getMenuName();
    if (menuRequestDto.getDescription() != null) {
      this.description = menuRequestDto.getDescription();
    }
    if (menuRequestDto.getPrice() != null) {
      this.price = menuRequestDto.getPrice();
    }
    this.shop = shop;
  }

  public void update(MenuRequestDto menuRequestDto) {
    if (menuRequestDto.getMenuName() != null) {
      this.menuName = menuRequestDto.getMenuName();
    }
    if (menuRequestDto.getDescription() != null) {
      this.description = menuRequestDto.getDescription();
    }
    if (menuRequestDto.getPrice() != null) {
      this.price = menuRequestDto.getPrice();
    }
    if (menuRequestDto.getIsHidden() != null) {
      this.isHidden = menuRequestDto.getIsHidden();
    }
  }
}
