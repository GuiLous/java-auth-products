package com.crud.crud.refreshToken.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.crud.DTOS.RefreshTokenDTO;
import com.crud.crud.refreshToken.services.RefreshTokenService;

@RestController
@RequestMapping("auth")
public class RefreshTokenController {
  @Autowired
  RefreshTokenService refreshTokenService;

  @PostMapping("/refresh-token")
  public ResponseEntity<Object> refreshToken(@RequestBody @Valid RefreshTokenDTO data) {
    return this.refreshTokenService.createNewToken(data.refreshToken());
  }
}
