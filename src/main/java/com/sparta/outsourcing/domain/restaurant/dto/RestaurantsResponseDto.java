package com.sparta.outsourcing.domain.restaurant.dto;

import com.sparta.outsourcing.domain.restaurant.entity.Restaurants;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class RestaurantsResponseDto {
  private Long restaurantId;
  private String name;
  private String category;
  private String address;
  private String number;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
  private LocalDateTime deletedDate;

  public RestaurantsResponseDto (Restaurants restaurants) {
    this.restaurantId = restaurants.getRestaurantId();
    this.name = restaurants.getName();
    this.category = restaurants.getCategory();
    this.address = restaurants.getAddress();
    this.number = restaurants.getNumber();
    this.createdDate = restaurants.getCreatedDate();
    this.updatedDate = restaurants.getUpdatedDate();
    this.deletedDate = restaurants.getDeletedDate();
  }
}
