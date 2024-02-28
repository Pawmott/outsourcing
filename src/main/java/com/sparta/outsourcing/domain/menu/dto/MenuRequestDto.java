package com.sparta.outsourcing.domain.menu.dto;

import com.sparta.outsourcing.domain.menu.entity.Menu;
import lombok.Getter;

@Getter
public class MenuRequestDto {

    private String name;
    private String category;
    private Long price;
    private String description;


    public Menu toEntity() {
        return Menu.builder()
                .name(this.name)
                .category(this.category)
                .price(this.price)
                .description(this.description).build();
    }

}
