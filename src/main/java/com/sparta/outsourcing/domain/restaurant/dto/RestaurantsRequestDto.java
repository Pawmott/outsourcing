package com.sparta.outsourcing.domain.restaurant.dto;

import com.sparta.outsourcing.domain.restaurant.entity.Restaurants;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RestaurantsRequestDto {

  private String name;
  private String category;
  private String address;
  private String number;

  @Builder
  public Restaurants toEntity() {
    return Restaurants.builder()
        .name(this.name)
        .category(this.category)
        .address(this.address)
        .number(this.number).build();
  }
}
