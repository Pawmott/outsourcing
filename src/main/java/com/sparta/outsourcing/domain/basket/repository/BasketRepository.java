package com.sparta.outsourcing.domain.basket.repository;

import com.sparta.outsourcing.domain.basket.model.Basket;
import java.util.List;

public interface BasketRepository {

  void register(Basket basket);

  void deleteBasket(Long memberId);

  List<Basket> basketInfo(Long memberId);
}
