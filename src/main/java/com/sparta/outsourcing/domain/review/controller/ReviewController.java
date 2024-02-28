package com.sparta.outsourcing.domain.review.controller;

import com.sparta.outsourcing.domain.review.model.dto.ReviewRequestDto;
import com.sparta.outsourcing.domain.review.model.dto.ReviewResponseDto;
import com.sparta.outsourcing.domain.review.service.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

  private final ReviewService reviewService;


  @PostMapping
  public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewRequestDto requestDto,
      @AuthenticationPrincipal UserDetails userDetails) {
    ReviewResponseDto reviewResponseDto = reviewService.createReview(requestDto, userDetails);
    return ResponseEntity.ok(reviewResponseDto);
  }

  @GetMapping
  public ResponseEntity<List<ReviewResponseDto>> findReviews(
      @RequestParam(required = false) Long restaurantId,
      @AuthenticationPrincipal UserDetails userDetails) {
    List<ReviewResponseDto> reviews = reviewService.findReviews(userDetails, restaurantId);
    return ResponseEntity.ok(reviews);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReviewResponseDto> findReviewById(@PathVariable Long id,
      @AuthenticationPrincipal UserDetails userDetails) {
    ReviewResponseDto reviewResponseDto = reviewService.findReviewById(id, userDetails);
    return ResponseEntity.ok(reviewResponseDto);
  }

  @GetMapping("/user")
  public ResponseEntity<List<ReviewResponseDto>> findAllReviewsByUser(
      @AuthenticationPrincipal UserDetails userDetails) {
    List<ReviewResponseDto> reviews = reviewService.findAllReviewsByUser(userDetails);
    return ResponseEntity.ok(reviews);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long id,
      @RequestBody ReviewRequestDto requestDto,
      @AuthenticationPrincipal UserDetails userDetails) {
    ReviewResponseDto updatedReview = reviewService.updateReview(id, requestDto, userDetails);
    return ResponseEntity.ok(updatedReview);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReview(@PathVariable Long id,
      @AuthenticationPrincipal UserDetails userDetails) {
    reviewService.deleteReview(id, userDetails);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}