package com.sparta.outsourcing.domain.restaurant.service;

import com.sparta.outsourcing.domain.restaurant.dto.RestaurantsRequestDto;
import com.sparta.outsourcing.domain.restaurant.dto.RestaurantsResponseDto;
import com.sparta.outsourcing.domain.restaurant.entity.Restaurants;
import com.sparta.outsourcing.domain.restaurant.repository.RestaurantsRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantsService {

  private final RestaurantsRepository restaurantsRepository;

  @Transactional
  public RestaurantsResponseDto createRestaurant(RestaurantsRequestDto restaurantsRequestDto) {

    Restaurants restaurants = restaurantsRequestDto.toEntity();
    restaurantsRepository.save(restaurants);
    RestaurantsResponseDto restaurantsResponseDto = new RestaurantsResponseDto(restaurants);
    //
    return restaurantsResponseDto;
  }

  public RestaurantsResponseDto getRestaurant(Long restaurantId) {
    Restaurants foundRestaurant = findByRestaurantId(restaurantId);
    RestaurantsResponseDto restaurantsResponseDto = new RestaurantsResponseDto(foundRestaurant);
    return restaurantsResponseDto;
  }


  public List<RestaurantsResponseDto> getRestaurantList() {
    return restaurantsRepository.findAllByOrderByRestaurantId().stream()
        .map(RestaurantsResponseDto::new).toList();

  } // for each문이랑 유사

  @Transactional
  public long deleteRestaurant(Long restaurantId) {
    deleteByRestaurantId(restaurantId);
    return restaurantId;
  }

  @Transactional
  public RestaurantsResponseDto updateRestaurant(Long restaurantId,
      RestaurantsRequestDto restaurantsRequestDto) {
    Restaurants restaurants = findByRestaurantId(restaurantId);
    restaurants.update(restaurantsRequestDto);
    Restaurants updatedRestaurant = restaurantsRepository.save(restaurants);
    RestaurantsResponseDto restaurantsResponseDto = new RestaurantsResponseDto(updatedRestaurant);
    return restaurantsResponseDto;
  }

  private void deleteByRestaurantId(Long restaurantId) {
    Optional<Restaurants> restaurantsOptional = restaurantsRepository.findById(restaurantId);

    if (restaurantsOptional.isEmpty()) {
      throw new IllegalArgumentException("삭제할 가게 정보가 존재하지 않습니다");
    } else {
      restaurantsRepository.deleteById(restaurantId);
    }
  }

  private Restaurants findByRestaurantId(Long restaurantId) {
    return restaurantsRepository.findById(restaurantId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 가게입니다"));
  }
}
