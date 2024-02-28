package com.sparta.outsourcing.domain.payment.dto;

import com.sparta.outsourcing.domain.payment.entity.Payments;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PaymentsRequestDto {

  private Short count;
  private Integer price;
}
