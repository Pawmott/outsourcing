package com.sparta.outsourcing.domain.order.model.entity;

import com.sparta.outsourcing.domain.order.model.Order;
import com.sparta.outsourcing.domain.order.model.OrderType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql = "update orders set deleted_date = NOW() where id = ?")
@SQLRestriction(value = "deleted_date is NULL and order_status != 'COMPLETED'")
@Table(name = "orders")
public class OrderEntity extends OrderTimestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long memberId;

  @Column(nullable = false)
  private Long restaurantId;

  @Column(nullable = false, length = 50)
  @Enumerated(EnumType.STRING)
  private OrderType orderStatus;

  public static OrderEntity of(Long memberId, Long restaurantId, OrderType orderStatus) {

    return OrderEntity.builder()
        .memberId(memberId)
        .restaurantId(restaurantId)
        .orderStatus(orderStatus)
        .build();
  }

  public Order toModel() {

    return Order.builder()
        .id(id)
        .memberId(memberId)
        .restaurantId(restaurantId)
        .orderStatus(orderStatus)
        .build();
  }

  public void updateCancel() {
    this.orderStatus = OrderType.CANCEL;
  }

  public void update() {
    if(this.orderStatus.equals(OrderType.WAITING)) {
      this.orderStatus = OrderType.DELIVERY;
    } else {
      this.orderStatus = OrderType.COMPLETED;
    }
  }
}
