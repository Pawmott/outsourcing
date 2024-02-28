package com.sparta.outsourcing.domain.basket.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BasketRequestDto {

  private Long restaurantId;
  private Long menuId;
  private int count;
}
