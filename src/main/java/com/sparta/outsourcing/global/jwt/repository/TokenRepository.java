package com.sparta.outsourcing.global.jwt.repository;

import com.sparta.outsourcing.global.jwt.entity.RefreshTokenEntity;

public interface TokenRepository {

  void register(Long memberId, String token);

  RefreshTokenEntity findByMemberId(Long memberId);

  void deleteToken(RefreshTokenEntity token);
}
