package com.crud.crud.auth.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.crud.DTOS.AuthenticationDTO;
import com.crud.crud.DTOS.RegisterDTO;
import com.crud.crud.auth.services.AuthorizationService;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data) {
        return this.authorizationService.login(data);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO data) {
        return this.authorizationService.register(data);
    }
}
