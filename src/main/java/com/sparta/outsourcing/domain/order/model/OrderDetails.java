package com.sparta.outsourcing.domain.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {

  private Long id;
  private Long orderId;
  private Long menuId;
  private int count;
}
