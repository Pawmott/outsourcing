package com.sparta.outsourcing.domain.restaurant.entity;

import com.sparta.outsourcing.domain.restaurant.dto.RestaurantsRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Where(clause = "deleted_date IS NULL")
@Table(name = "restaurants")
@SQLDelete(sql = "update restaurants set deleted = true,deleted_date = NOW() where restaurant_id= ?")
// deleted_date가 null인 가게만 조회

public class Restaurants {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long restaurantId;
  @Column(length = 50, unique = true)
  @NotNull
  private String name;
  @Column(length = 20)
  @NotNull
  private String category;
  @Column
  @NotNull
  private String address;
  @Column(length = 13)
  @NotNull
  private String number;
  @Column
  @NotNull
  @CreatedDate
  private LocalDateTime createdDate;
  @Column
  private LocalDateTime updatedDate;

  @Column
  @LastModifiedDate
  private LocalDateTime deletedDate;

  @Column(nullable = false)
  private boolean deleted = false;

  @Builder
  public Restaurants(String name, String category, String address, String number) {
    this.name = name;
    this.category = category;
    this.address = address;
    this.number = number;
    this.createdDate = LocalDateTime.now();
  }

  public void update(RestaurantsRequestDto restaurants) {
    this.name = restaurants.getName();
    this.category = restaurants.getCategory();
    this.address = restaurants.getAddress();
    this.number = restaurants.getNumber();
    this.updatedDate = LocalDateTime.now();
  }

  public Restaurants() {

  }
}








