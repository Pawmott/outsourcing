package com.sparta.outsourcing.domain.order.service;

import com.sparta.outsourcing.domain.basket.model.Basket;
import com.sparta.outsourcing.domain.basket.repository.BasketRepository;
import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.repository.member.MemberRepository;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.order.model.Order;
import com.sparta.outsourcing.domain.order.model.OrderDetails;
import com.sparta.outsourcing.domain.order.model.OrderType;
import com.sparta.outsourcing.domain.order.model.entity.OrderDetailsEntity;
import com.sparta.outsourcing.domain.order.model.entity.OrderEntity;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.order.service.dto.MenuInfoDto;
import com.sparta.outsourcing.domain.order.service.dto.OrderInfoResponse;
import com.sparta.outsourcing.domain.order.service.dto.OrderResponseDto;
import com.sparta.outsourcing.domain.payment.entity.Payments;
import com.sparta.outsourcing.domain.payment.repository.PaymentsRepository;
import com.sparta.outsourcing.domain.restaurant.repository.RestaurantsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

  private final MemberRepository memberRepository;
  private final OrderRepository orderRepository;
  private final BasketRepository basketRepository;
  private final RestaurantsRepository restaurantsRepository;
  private final MenuRepository menuRepository;
  private final PaymentsRepository paymentsRepository;

  public OrderResponseDto order(UserDetails userDetails) {
    Member member = memberRepository.findMemberOrElseThrow(userDetails.getUsername());

    List<Basket> basketList = basketRepository.basketInfo(member.getId());

    if (basketList.isEmpty()) {
      throw new IllegalArgumentException("장바구니가 비어있습니다.");
    }

    if (!isSingleRestaurantOrder(basketList)) {
      throw new IllegalArgumentException("주문은 하나의 가게에만 할 수 있습니다.");
    }

    Long restaurantId = basketList.get(0).getRestaurantId();

    Long orderId = orderRepository.registerOrder(member.getId(), restaurantId);
    orderRepository.registerOrderDetails(orderId, basketList);

    registerPayment(basketList, restaurantId, orderId);

    basketRepository.deleteBasket(member.getId());

    return OrderResponseDto.builder()
        .memberId(member.getId())
        .orderId(orderId)
        .build();
  }

  @Transactional(readOnly = true)
  public OrderInfoResponse orderInfo(Long orderId) {
    Order order = orderRepository.findByOrderId(orderId).toModel();

    List<OrderDetails> orderDetailsList = orderRepository.findOrderDetailsByOrderId(orderId)
        .stream().map(OrderDetailsEntity::toModel).toList();

    String restaurantName = restaurantsRepository.findById(order.getRestaurantId()).orElseThrow(
        () -> new EntityNotFoundException("해당 가게가 존재하지 않습니다.")
    ).getName();

    List<MenuInfoDto> menuInfoDtoList = orderDetailsList.stream().map(
        orderDetails -> new MenuInfoDto(orderDetails.getMenuId(), orderDetails.getCount())
    ).toList();

    return OrderInfoResponse.builder()
        .orderId(orderId)
        .memberId(order.getMemberId())
        .restaurantName(restaurantName)
        .orderStatus(order.getOrderStatus())
        .menuInfoDtoList(menuInfoDtoList)
        .build();
  }

  public void orderDelete(Long orderId) {
    OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
    OrderType orderStatus = orderEntity.getOrderStatus();

    if (orderStatus.equals(OrderType.DELIVERY)) {
      throw new IllegalArgumentException("현재 배달 중이므로 결제를 취소할 수 없습니다.");
    }

    orderRepository.updatedCancel(orderId);
    orderRepository.deleteOrderAll(orderId);
    //payments도 삭제해야함.
  }

  private boolean isSingleRestaurantOrder(List<Basket> basketList) {
    if (basketList.size() == 1) {
      return true;
    }

    for (int i = 1; i < basketList.size(); i++) {
      Basket prevBasket = basketList.get(i - 1);
      Basket currentBasket = basketList.get(i);

      if (!prevBasket.checkRestaurantId(currentBasket.getRestaurantId())) {
        return false;
      }
    }
    return true;
  }

  private void registerPayment(List<Basket> basketList, Long restaurantId, Long orderId) {
    int totalPrice = 0;

    for (Basket basket : basketList) {
      int count = basket.getCount();
      Long menuId = basket.getMenuId();
      Menu menu = menuRepository.findByRestaurantsIdAndId(restaurantId, menuId);
      int price = menu.getPrice();

      totalPrice += count * price;
    }

    Payments payments = Payments.builder()
        .orderId(orderId)
        .totalPrice(totalPrice)
        .createdDate(LocalDateTime.now())
        .build();

    paymentsRepository.save(payments);
  }

}
