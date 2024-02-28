package com.sparta.outsourcing.domain.order.repository;

import com.sparta.outsourcing.domain.basket.model.Basket;
import com.sparta.outsourcing.domain.order.model.entity.OrderDetailsEntity;
import com.sparta.outsourcing.domain.order.model.entity.OrderEntity;
import java.util.List;

public interface OrderRepository {

  Long registerOrder(Long memberId, Long restaurantId);

  void registerOrderDetails(Long orderId, List<Basket> basketList);

  OrderEntity findByOrderId(Long orderId);

  List<OrderDetailsEntity> findOrderDetailsByOrderId(Long orderId);

  void deleteOrderAll(Long orderId);

  void updatedCancel(Long orderId);

  List<OrderEntity> findAll();
}
