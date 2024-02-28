package com.sparta.outsourcing.domain.favorite.service;

import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import com.sparta.outsourcing.domain.member.repository.member.MemberJpaRepository;
import com.sparta.outsourcing.domain.restaurant.repository.RestaurantsRepository;
import com.sparta.outsourcing.domain.favorite.model.dto.FavoriteRequestDto;
import com.sparta.outsourcing.domain.favorite.model.dto.FavoriteResponseDto;
import com.sparta.outsourcing.domain.favorite.model.entity.Favorite;
import com.sparta.outsourcing.domain.favorite.repository.FavoriteRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class FavoriteService {

  private final FavoriteRepository favoriteRepository;
  private final RestaurantsRepository restaurantsRepository;
  private final MemberJpaRepository memberJpaRepository;

  public FavoriteResponseDto createFavorite(FavoriteRequestDto requestDto, UserDetails userDetails) {
    MemberEntity member = memberJpaRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "좋아요를 등록할 유저를 찾을 수 없습니다."));

    if (!restaurantsRepository.existsById(requestDto.getRestaurantId())) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "좋아요를 등록할 가게를 찾을 수 없습니다.\"");
    }

    boolean favoriteExists = favoriteRepository.existsByMemberIdAndRestaurantId(member.getId(), requestDto.getRestaurantId());
    if (favoriteExists) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 해당 가게에 좋아요를 등록했습니다.");
    }

    Favorite favorite = new Favorite();
    favorite.setMemberId(member.getId());
    favorite.setRestaurantId(requestDto.getRestaurantId());
    favorite.setCreatedDate(LocalDate.now());

    Favorite savedFavorite = favoriteRepository.save(favorite);
    return new FavoriteResponseDto(savedFavorite.getId(), savedFavorite.getMemberId(),
        savedFavorite.getRestaurantId(), savedFavorite.getCreatedDate());
  }

  public List<FavoriteResponseDto> findAllFavoritesByMemberId(UserDetails userDetails) {
    MemberEntity member = memberJpaRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저가 등록한 좋아요가 없습니다."));

    List<Favorite> favorites = favoriteRepository.findAllByMemberId(member.getId());
    return favorites.stream()
        .map(favorite -> new FavoriteResponseDto(favorite.getId(), favorite.getMemberId(),
            favorite.getRestaurantId(), favorite.getCreatedDate()))
        .collect(Collectors.toList());
  }

  public FavoriteResponseDto findFavoritesByRestaurantId(Long restaurantId, UserDetails userDetails) {
    MemberEntity member = memberJpaRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "좋아요를 등록한 유저를 찾을 수 없습니다"));

    if (!restaurantsRepository.existsById(restaurantId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "가게를 찾을 수 없습니다.");
    }

    Favorite favorite = favoriteRepository.findByRestaurantIdAndMemberId(restaurantId,
            member.getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "좋아요를 찾을 수 없습니다."));

    return new FavoriteResponseDto(favorite.getId(), favorite.getMemberId(),
        favorite.getRestaurantId(), favorite.getCreatedDate());
  }

  public void deleteFavorite(Long restaurantId, UserDetails userDetails) {

    MemberEntity member = memberJpaRepository.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저 인증에 실패했습니다."));


    Favorite favorite = favoriteRepository.findByRestaurantIdAndMemberId(restaurantId, member.getId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "좋아요를 찾을 수 없습니다."));

    favoriteRepository.delete(favorite);
  }

}
