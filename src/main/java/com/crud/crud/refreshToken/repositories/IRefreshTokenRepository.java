package com.crud.crud.refreshToken.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crud.crud.refreshToken.model.RefreshTokenModel;

public interface IRefreshTokenRepository extends JpaRepository<RefreshTokenModel, UUID> {
  RefreshTokenModel findByToken(String token);

  RefreshTokenModel findByUserInfoId(UUID user_id);
}
