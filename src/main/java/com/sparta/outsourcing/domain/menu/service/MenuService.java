package com.sparta.outsourcing.domain.menu.service;

import com.sparta.outsourcing.domain.menu.dto.MenuRequestDto;
import com.sparta.outsourcing.domain.menu.dto.MenuResponseDto;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuResponseDto insert(MenuRequestDto menuRequestDto, String username) {
        Menu menu = menuRequestDto.toEntity();
        menuRepository.save(menu);
        return new MenuResponseDto(menu);
    }

    public MenuResponseDto getMenu(Long id) {
        Menu findMenu = findById(id);
        return new MenuResponseDto(findMenu);
    }

    public List<MenuResponseDto> getMenuList() {
        return menuRepository.findAll().stream().map(MenuResponseDto::new).toList();
    }

    public MenuResponseDto updateMenu(Long id, MenuRequestDto menuRequestDto) {
        Menu menu = findById(id);
        menu.update(menuRequestDto);
        Menu updateMenu = menuRepository.save(menu);
        return new MenuResponseDto(updateMenu);

    }

    public Long deleteMenu(Long id) {
        deleteById(id);

        return id;
    }

    private Menu findById(Long id) {
        return menuRepository.findById(id).orElseThrow
                (() -> new IllegalArgumentException("없는 메뉴 입니다."));
    }

    private Long deleteById(Long id) {
        menuRepository.deleteById(id);

        return id;
    }
}
