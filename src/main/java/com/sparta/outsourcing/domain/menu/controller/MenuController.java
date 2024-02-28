package com.sparta.outsourcing.domain.menu.controller;

import com.sparta.outsourcing.domain.menu.dto.MenuRequestDto;
import com.sparta.outsourcing.domain.menu.dto.MenuResponseDto;
import com.sparta.outsourcing.domain.menu.dto.CommonResponse;
import com.sparta.outsourcing.domain.menu.service.MenuService;
import com.sparta.outsourcing.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/menus")
    public ResponseEntity<MenuResponseDto> insertMenu(
            @RequestBody MenuRequestDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.insert(request, userDetails.getUsername()));
    }

    @GetMapping("/menus/{id}")
    public ResponseEntity<CommonResponse<MenuResponseDto>> getMenu(@PathVariable Long id) {
        MenuResponseDto menuResponseDto = menuService.getMenu(id);

        return ResponseEntity.ok().body(CommonResponse.<MenuResponseDto>builder().code(HttpStatus.OK.value())
                .message("메뉴 단건 조회가 완료되었습니다.").data(menuResponseDto).build());
    }

    @GetMapping("/menus")
    public ResponseEntity<CommonResponse<List<MenuResponseDto>>> getMenuList() {
        List<MenuResponseDto> menuResponseDto = menuService.getMenuList();

        return ResponseEntity.ok().body(CommonResponse.<List<MenuResponseDto>>builder().code(HttpStatus.OK.value())
                .message("메뉴 전체 조회 완료되었습니다.").data(menuResponseDto).build());
    }

    @PutMapping("/menus/{id}")
    public ResponseEntity<CommonResponse<MenuResponseDto>> updateMenu(@PathVariable Long id, @RequestBody MenuRequestDto menuRequestDto) {
        MenuResponseDto menuResponseDto = menuService.updateMenu(id, menuRequestDto);

        return ResponseEntity.ok().body(CommonResponse.<MenuResponseDto>builder().code(HttpStatus.OK.value())
                .message("메뉴 수정 완료 되었습니다.").data(menuResponseDto).build());
    }

    @DeleteMapping("/menus/{id}")
    public ResponseEntity<CommonResponse> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);

        return ResponseEntity.ok().body(CommonResponse.<String>builder().code(HttpStatus.OK.value())
                .message("메뉴 삭제 완료").build());
    }
}
