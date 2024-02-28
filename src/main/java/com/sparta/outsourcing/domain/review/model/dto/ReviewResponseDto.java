package com.sparta.outsourcing.domain.review.model.dto;

import com.sparta.outsourcing.domain.review.model.entity.Review;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewResponseDto {

  private Long id;
  private String contents;
  private int score;
  private Long memberEntityId;
  private Long restaurantId;
  private Long menuId;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  public ReviewResponseDto(Review review) {
    this.id = review.getId();
    this.contents = review.getContents();
    this.score = review.getScore();
    this.memberEntityId = review.getMemberEntityId();
    this.restaurantId = review.getRestaurantId();
    this.menuId = review.getMenuId();
    this.createdDate = review.getCreatedDate();
    this.updatedDate = review.getUpdatedDate();
  }


}