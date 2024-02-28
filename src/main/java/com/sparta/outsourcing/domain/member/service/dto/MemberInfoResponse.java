package com.sparta.outsourcing.domain.member.service.dto;

import com.sparta.outsourcing.domain.favorite.model.entity.Favorite;
import com.sparta.outsourcing.domain.review.model.entity.Review;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoResponse {
  private Long memberId;
  private List<Favorite> favoriteList;
  private List<Review> reviewList;
}
