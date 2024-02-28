package com.sparta.outsourcing.domain.order.service.dto;

import com.sparta.outsourcing.domain.order.model.OrderType;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderInfoResponse {
  private Long orderId;
  private Long memberId;
  private String restaurantName;
  private OrderType orderStatus;
  private List<MenuInfoDto> menuInfoDtoList;
}
