package com.sparta.outsourcing.domain.order.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponseDto {
  private Long memberId;
  private Long orderId;
}
