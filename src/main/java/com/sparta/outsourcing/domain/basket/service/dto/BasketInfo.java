package com.sparta.outsourcing.domain.basket.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BasketInfo {

  private Long memberId;
  private Long restaurantId;
  private Long menuId;
  private int count;
}
