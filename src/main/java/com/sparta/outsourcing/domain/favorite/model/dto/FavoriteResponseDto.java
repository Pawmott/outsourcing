package com.sparta.outsourcing.domain.favorite.model.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FavoriteResponseDto {

  private Long id;
  private Long memberId;
  private Long restaurantId;
  private LocalDate createdDate;

}
