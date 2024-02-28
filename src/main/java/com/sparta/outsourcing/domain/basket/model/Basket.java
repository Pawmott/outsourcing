package com.sparta.outsourcing.domain.basket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Basket {

  private Long memberId;
  private Long restaurantId;
  private Long menuId;
  private int count;

  public boolean checkRestaurantId(Long restaurantId) {
    return this.restaurantId.equals(restaurantId);
  }
}
