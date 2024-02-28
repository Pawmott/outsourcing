package com.sparta.outsourcing.domain.scheduler;

import com.sparta.outsourcing.domain.order.model.entity.OrderEntity;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "scheduler")
@Component
@RequiredArgsConstructor
public class Scheduler {

  private final OrderRepository orderRepository;

  // 5분마다 전체적으로 배달의 상태를 변경, 처음 프로그램이 실행 후 10초 뒤에 시작
  @Scheduled(fixedDelay = 60000, initialDelay = 10000)
  @Transactional
  public void updateOrderType() {
    log.info("주문 상태 업데이트 실행");
    List<OrderEntity> orderEntities = orderRepository.findAll();

    orderEntities.forEach(OrderEntity::update); // 주문 상태의 정보를 수정
  }
}
