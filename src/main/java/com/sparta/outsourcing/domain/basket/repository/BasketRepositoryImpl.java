package com.sparta.outsourcing.domain.basket.repository;

import com.sparta.outsourcing.domain.basket.model.Basket;
import com.sparta.outsourcing.domain.basket.model.entity.BasketEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BasketRepositoryImpl implements BasketRepository {

  private final BasketJpaRepository basketJpaRepository;

  // 해당 유저의 아이디를 통해 장바구니에 아이템이 있는지 판단 -> 없다면 저장 있다면 중복이 되는지 체크 후 저장
  @Override
  public void register(Basket basket) {
    BasketEntity basketInfo = basketJpaRepository.findFirstByMemberIdAndAndMenuId(
        basket.getMemberId(), basket.getMenuId());

    if (basketInfo == null) {
      basketJpaRepository.save(
          BasketEntity.of(basket.getMemberId(), basket.getRestaurantId(), basket.getMenuId(),
              basket.getCount()));
      return;
    }

    basketInfo.update(basketInfo.getCount());
  }

  // 해당 유저가 시킨 장바구니 전체 정보를 삭제
  @Override
  public void deleteBasket(Long memberId) {
    List<BasketEntity> basketInfo = basketJpaRepository.findBasketEntityByMemberId(memberId);

    basketJpaRepository.deleteAll(basketInfo);
  }

  // 해당 유저의 장바구니 정보를 가져옴
  @Override
  public List<Basket> basketInfo(Long memberId) {
    List<BasketEntity> basketInfo = basketJpaRepository.findBasketEntityByMemberId(memberId);

    return basketInfo.stream().map(BasketEntity::toModel).toList();
  }
}
