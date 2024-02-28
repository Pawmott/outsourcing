package com.sparta.outsourcing.domain.basket.model.entity;

import com.sparta.outsourcing.domain.basket.model.Basket;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "basket")
public class BasketEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long memberId;

  @Column(nullable = false)
  private Long restaurantId;

  @Column(nullable = false)
  private Long menuId;

  @Column(nullable = false)
  private int count;

  public static BasketEntity of(Long memberId, Long restaurantId, Long menuId, int count) {

    return BasketEntity.builder()
        .memberId(memberId)
        .restaurantId(restaurantId)
        .menuId(menuId)
        .count(count)
        .build();
  }

  public void update(int count) {
    this.count += count;
  }

  public Basket toModel() {

    return Basket.builder()
        .memberId(memberId)
        .restaurantId(restaurantId)
        .menuId(menuId)
        .count(count)
        .build();
  }
}
