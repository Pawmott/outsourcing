package com.sparta.outsourcing.domain.menu.entity;

import com.sparta.outsourcing.domain.menu.dto.MenuRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table
@SQLDelete(sql = "update menu set deleted_date = NOW() where id = ?")
@SQLRestriction(value = "deleted_date is NULL")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private Long restaurantsId;
    @Column(length = 20)
    @NotNull
    private String name;
    @Column(length = 20)
    @NotNull
    private String category;
    @Column
    @NotNull
    private Long price;
    @Column(length = 255)
    @NotNull
    private String description;
    @Column
    @NotNull
    @CreatedDate
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime updatedDate;
    @Column
    @LastModifiedDate
    private LocalDateTime deletedDate;

    @Builder
    public Menu(Long restaurantsId, String name, String category, Long price, String description) {
        this.restaurantsId = restaurantsId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdDate = LocalDateTime.now();
    }

    public void update(MenuRequestDto menu) {
        this.name = menu.getName();
        this.category = menu.getCategory();
        this.price = menu.getPrice();
        this.description = menu.getDescription();
        this.createdDate = LocalDateTime.now();
    }
}
