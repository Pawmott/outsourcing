package com.sparta.outsourcing.domain.favorite.controller;

import com.sparta.outsourcing.domain.favorite.model.dto.FavoriteRequestDto;
import com.sparta.outsourcing.domain.favorite.model.dto.FavoriteResponseDto;
import com.sparta.outsourcing.domain.favorite.service.FavoriteService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
public class FavoriteController {

  private final FavoriteService favoriteService;

  @PostMapping
  public ResponseEntity<FavoriteResponseDto> addFavorite(
      @RequestBody FavoriteRequestDto requestDto,
      @AuthenticationPrincipal UserDetails userDetails) {
    FavoriteResponseDto favoriteResponseDto = favoriteService.createFavorite(requestDto, userDetails);
    return ResponseEntity.status(HttpStatus.CREATED).body(favoriteResponseDto);
  }

  @GetMapping
  public ResponseEntity<List<FavoriteResponseDto>> findAllFavoritesByMember(
      @AuthenticationPrincipal UserDetails userDetails) {
    List<FavoriteResponseDto> favorites = favoriteService.findAllFavoritesByMemberId(userDetails);
    return ResponseEntity.ok(favorites);
  }

  @GetMapping("/{restaurantId}")
  public ResponseEntity<FavoriteResponseDto> findFavoritesByRestaurantId(
      @PathVariable Long restaurantId,
      @AuthenticationPrincipal UserDetails userDetails) {
    FavoriteResponseDto favoriteResponseDto = favoriteService.findFavoritesByRestaurantId(restaurantId, userDetails);
    return ResponseEntity.ok(favoriteResponseDto);
  }

  @DeleteMapping("/{restaurantId}")
  public ResponseEntity<Void> deleteFavorite(
      @PathVariable Long restaurantId,
      @AuthenticationPrincipal UserDetails userDetails) {
    favoriteService.deleteFavorite(restaurantId, userDetails);
    return ResponseEntity.noContent().build();
  }
}
