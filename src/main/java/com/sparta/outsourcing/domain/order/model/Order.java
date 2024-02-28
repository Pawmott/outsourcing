package com.sparta.outsourcing.domain.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

  private Long id;
  private Long memberId;
  private Long restaurantId;
  private OrderType orderStatus;
}
