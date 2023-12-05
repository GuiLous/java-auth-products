package com.crud.crud.refreshToken.model;

import java.time.Instant;
import java.util.UUID;

import com.crud.crud.user.models.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_refresh_token")
public class RefreshTokenModel {
  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;
  private String token;
  private Instant expiryDate;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private UserModel userInfo;
  public RefreshTokenModel(String token, Instant expireDate, UserModel user) {
    this.token = token;
    this.expiryDate = expireDate;
    this.userInfo = user;
  }

}
