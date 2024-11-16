package com.sparta.deliveryminiproject.domain.order.entity;

import com.sparta.deliveryminiproject.domain.cart.entity.Cart;
import com.sparta.deliveryminiproject.domain.payment.entity.Payment;
import com.sparta.deliveryminiproject.domain.user.entity.User;
import com.sparta.deliveryminiproject.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "p_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private int totalPrice;

  private String requests;

  private String address;

  private String phoneNumber;

  @Setter
  @Enumerated(value = EnumType.STRING)
  private OrderType orderType;

  @Setter
  @Enumerated(value = EnumType.STRING)
  private OrderStatus orderStatus;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<Cart> cartList = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_id")
  @Setter
  private Payment payment;

  @Builder
  private Order(UUID id, int totalPrice, String requests, String address, String phoneNumber,
      OrderType orderType, OrderStatus orderStatus, User user, Payment payment) {
    this.id = id;
    this.totalPrice = totalPrice;
    this.requests = requests;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.orderType = orderType;
    this.orderStatus = orderStatus;
    this.user = user;
    this.payment = payment;
  }

  public void addCartList(Cart cart) {
    cartList.add(cart);
    cart.setOrder(this);
  }

  public void calculateAndSetTotalPrice() {
    totalPrice = cartList.stream()
        .map(cart -> cart.getQuantity() * cart.getMenu().getPrice())
        .mapToInt(Integer::intValue)
        .sum() + cartList.get(0).getShop().getDeliveryTip();
  }
}
