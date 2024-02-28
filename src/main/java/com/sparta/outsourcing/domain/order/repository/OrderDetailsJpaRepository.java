package com.sparta.outsourcing.domain.order.repository;

import com.sparta.outsourcing.domain.order.model.entity.OrderDetailsEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsJpaRepository extends JpaRepository<OrderDetailsEntity, Long> {
  List<OrderDetailsEntity> findOrderDetailsEntitiesByOrderId(Long orderId);
}
