package com.sparta.outsourcing.domain.review.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {

  @NotNull()
  private String contents;
  @NotNull()
  @Min(value = 0)
  @Max(value = 5)
  private Integer score;
  @NotNull()
  private Long restaurantId;
  @NotNull()
  private Long menuId;


}
