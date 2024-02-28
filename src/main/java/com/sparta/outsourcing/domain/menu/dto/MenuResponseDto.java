package com.sparta.outsourcing.domain.menu.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuResponseDto {
    private Long id;
    private Long restaurantsId;
    private String name;
    private String category;
    private Long price;
    private String description;

    public MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.restaurantsId = getRestaurantsId();
        this.name = menu.getName();
        this.category = menu.getCategory();
        this.price = menu.getPrice();
        this.description = menu.getDescription();
    }
}
