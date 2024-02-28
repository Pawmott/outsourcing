package com.sparta.outsourcing.domain.review.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
@Where(clause = "deleted_date IS NULL")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "review")
public class Review {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  @Column
  private String contents;
  @NotNull
  @Column
  @Min(0)
  @Max(5)
  private int score;
  @Column
  @NotNull
  @CreatedDate
  private LocalDateTime createdDate;
  @Column
  @NotNull
  private LocalDateTime updatedDate;
  @Column
  @LastModifiedDate
  private LocalDateTime deletedDate;


  @Column(nullable = false)
  @NotNull
  private Long memberEntityId;

  @Column(nullable = false)
  @NotNull
  private Long restaurantId;

  @Column(nullable = false)
  @NotNull
  private Long menuId;


}
