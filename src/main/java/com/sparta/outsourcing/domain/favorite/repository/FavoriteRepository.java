package com.sparta.outsourcing.domain.favorite.repository;

import com.sparta.outsourcing.domain.favorite.model.entity.Favorite;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {


  List<Favorite> findAllByMemberId(Long memberId);

  Optional<Favorite> findByRestaurantIdAndMemberId(Long restaurantId, Long memberId);

  boolean existsByMemberIdAndRestaurantId(Long id, Long restaurantId);
}

