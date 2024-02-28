package com.sparta.outsourcing.domain.basket.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class BasketResponseDto {

  private Long memberId;
  private Long restaurantId;
  private Long menuId;
  private int count;
}
