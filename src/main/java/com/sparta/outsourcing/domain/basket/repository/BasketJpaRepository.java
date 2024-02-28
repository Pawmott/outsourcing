package com.sparta.outsourcing.domain.basket.repository;

import com.sparta.outsourcing.domain.basket.model.entity.BasketEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketJpaRepository extends JpaRepository<BasketEntity, Long> {

  List<BasketEntity> findBasketEntityByMemberId(Long memberId);

  BasketEntity findFirstByMemberIdAndAndMenuId(Long memberId, Long menuId);
}
