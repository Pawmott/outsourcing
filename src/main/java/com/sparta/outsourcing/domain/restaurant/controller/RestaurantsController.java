package com.sparta.outsourcing.domain.restaurant.controller;


import com.sparta.outsourcing.domain.restaurant.dto.RestaurantsRequestDto;
import com.sparta.outsourcing.domain.restaurant.dto.RestaurantsResponseDto;
import com.sparta.outsourcing.domain.restaurant.entity.CommonResponse;
import com.sparta.outsourcing.domain.restaurant.service.RestaurantsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RestaurantsController {

  private final RestaurantsService restaurantsService;

  @PostMapping("/restaurants")
  public ResponseEntity<CommonResponse<RestaurantsResponseDto>> createRestaurant(
      @RequestBody RestaurantsRequestDto requestDto) {
    RestaurantsResponseDto restaurantsResponseDto = restaurantsService.createRestaurant(requestDto);

    return ResponseEntity.ok().body(
        CommonResponse.<RestaurantsResponseDto>builder().code(HttpStatus.OK.value())
            .message("가게 생성이 완료되었습니다").data(restaurantsResponseDto).build());
  }

  @GetMapping("/restaurants/{restaurantId}")
  public ResponseEntity<CommonResponse<RestaurantsResponseDto>> getRestaurant(
      @PathVariable Long restaurantId) {
    RestaurantsResponseDto restaurantsResponseDto = restaurantsService.getRestaurant(restaurantId);

    return ResponseEntity.ok().body(
        CommonResponse.<RestaurantsResponseDto>builder().code(HttpStatus.OK.value())
            .message("가게 단건 조회가 완료되었습니다").data(restaurantsResponseDto).build());
  }

  @GetMapping("/restaurants")
  public ResponseEntity<CommonResponse<List<RestaurantsResponseDto>>> getRestaurantList() {
    List<RestaurantsResponseDto> restaurantsResponseDto = restaurantsService.getRestaurantList();

    return ResponseEntity.ok().body(
        CommonResponse.<List<RestaurantsResponseDto>>builder().code(HttpStatus.OK.value())
            .message("가게 전체 조회가 완료되었습니다").data(restaurantsResponseDto).build());
  }


  @PutMapping("/restaurants/{restaurantId}")
  public ResponseEntity<CommonResponse<RestaurantsResponseDto>> updateRestaurant(
      @PathVariable Long restaurantId,
      @RequestBody RestaurantsRequestDto restaurantsRequestDto) {

    RestaurantsResponseDto restaurantsResponseDto = restaurantsService.updateRestaurant(
        restaurantId, restaurantsRequestDto);

    return ResponseEntity.ok().body(
        CommonResponse.<RestaurantsResponseDto>builder().code(HttpStatus.OK.value())
            .message("가게 수정이 완료되었습니다").data(restaurantsResponseDto).build());
  }

  @DeleteMapping("/restaurants/{restaurantId}")
  public ResponseEntity<CommonResponse> deleteRestaurant(@PathVariable Long restaurantId) {
    restaurantsService.deleteRestaurant(restaurantId);

    return ResponseEntity.ok().body(
        CommonResponse.<String>builder().code(HttpStatus.OK.value())
            .message("가게 삭제가 완료되었습니다").build());
  }
}
