package com.crud.crud.auth.services;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.crud.crud.DTOS.AuthenticationDTO;
import com.crud.crud.DTOS.RefreshTokenDTO;
import com.crud.crud.DTOS.RegisterDTO;
import com.crud.crud.infra.security.TokenService;
import com.crud.crud.refreshToken.services.RefreshTokenService;
import com.crud.crud.user.models.UserModel;
import com.crud.crud.user.repositories.IUserRepository;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email);
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data) {
        AuthenticationManager authenticationManager = context.getBean(AuthenticationManager.class);

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = this.tokenService.generateToken((UserModel) auth.getPrincipal());

        var refreshToken = this.refreshTokenService.createRefreshToken(data.email());

        Map<String, Object> object = new HashMap<>();

        object.put("email", data.email());
        object.put("accessToken", token);
        object.put("refreshToken", refreshToken);

        return ResponseEntity.status(HttpStatus.OK).body(object);
    }

    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO data) {
        var userAlreadyExists = this.userRepository.findByEmail(data.email());

        if (userAlreadyExists != null)
            return ResponseEntity.badRequest().build();

        String hashedPassword = new BCryptPasswordEncoder().encode(data.password());
        UserModel newUser = new UserModel(data.email(), hashedPassword, data.role());

        this.userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Object> refreshToken(@RequestBody @Valid RefreshTokenDTO data) {

        return ResponseEntity.ok().build();
    }
}
