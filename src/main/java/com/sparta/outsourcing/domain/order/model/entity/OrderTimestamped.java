package com.sparta.outsourcing.domain.order.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class OrderTimestamped {
  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime orderedDate;

  private LocalDateTime deletedDate;
}
