package com.crud.crud.refreshToken.services;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.crud.crud.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crud.crud.refreshToken.model.RefreshTokenModel;
import com.crud.crud.refreshToken.repositories.IRefreshTokenRepository;
import com.crud.crud.user.repositories.IUserRepository;

@Service
public class RefreshTokenService {

  @Autowired
  private IRefreshTokenRepository refreshTokenRepository;

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private TokenService tokenService;

  public String createRefreshToken(String email) {
    var user = this.userRepository.findByEmail(email);

    var refreshTokenExists = this.refreshTokenRepository.findByUserInfoId(user.getId());

    var token = UUID.randomUUID().toString();
    var expiryDate = Instant.now().plusMillis(600000); // 10

    if (refreshTokenExists == null) {
      RefreshTokenModel refreshToken = new RefreshTokenModel(token, expiryDate, user);

      this.refreshTokenRepository.save(refreshToken);

      return refreshToken.getToken();
    }

    refreshTokenExists.setToken(token);
    refreshTokenExists.setExpiryDate(expiryDate);

    this.refreshTokenRepository.save(refreshTokenExists);

    return refreshTokenExists.getToken();
  }

  public ResponseEntity<Object> createNewToken(String refreshToken) {
    RefreshTokenModel refreshTokenExists = this.refreshTokenRepository.findByToken(refreshToken);

    if(refreshTokenExists == null) {
      throw new RuntimeException("Refresh token is not valid");
    }

    var tokenVerified = this.verifyExpiration(refreshTokenExists);

    var user = tokenVerified.getUserInfo();

    String accessToken = this.tokenService.generateToken(user);

    Map<String, Object> object = new HashMap<>();

    object.put("email", user.getEmail());
    object.put("accessToken", accessToken);
    object.put("refreshToken", refreshTokenExists.getToken());

    return ResponseEntity.status(HttpStatus.OK).body(object);
  }

  public RefreshTokenModel verifyExpiration(RefreshTokenModel token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      this.refreshTokenRepository.delete(token);
      throw new RuntimeException("Refresh token expired. Please make a new signin request");
    }

    return token;
  }
}
