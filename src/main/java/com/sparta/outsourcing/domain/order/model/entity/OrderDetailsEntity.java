package com.sparta.outsourcing.domain.order.model.entity;

import com.sparta.outsourcing.domain.order.model.OrderDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "orderDetails")
@SQLDelete(sql = "update order_details set deleted_date = NOW() where id = ?")
@SQLRestriction(value = "deleted_date is NULL")
public class OrderDetailsEntity extends OrderTimestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long orderId;

  @Column(nullable = false)
  private Long menuId;

  @Column(nullable = false)
  private int count;

  public static OrderDetailsEntity of(Long orderId, Long menuId, int count) {

    return OrderDetailsEntity.builder()
        .orderId(orderId)
        .menuId(menuId)
        .count(count)
        .build();
  }

  public OrderDetails toModel() {

    return OrderDetails.builder()
        .id(id)
        .orderId(orderId)
        .menuId(menuId)
        .count(count)
        .build();
  }
}
