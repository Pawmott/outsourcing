package com.sparta.outsourcing.domain.order.repository;

import com.sparta.outsourcing.domain.order.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

}
