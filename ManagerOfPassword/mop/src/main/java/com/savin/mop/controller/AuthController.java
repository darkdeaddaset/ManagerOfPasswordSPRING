package com.savin.mop.controller;

import com.savin.mop.request.JwtRequest;
import com.savin.mop.request.RefreshTokenRequest;
import com.savin.mop.response.JwtResponse;
import com.savin.mop.service.AuthService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AuthController {
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest){
        JwtResponse token;
        try {
            token = authService.login(jwtRequest);
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(token);
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> token(@RequestBody RefreshTokenRequest jwtRefreshToken){
        try {
            final JwtResponse token = authService.getAccessToken(jwtRefreshToken.getRefreshToken());
            return ResponseEntity.ok(token);
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshTokenRequest jwtRefreshToken){
        try {
            final JwtResponse token = authService.refresh(jwtRefreshToken.getRefreshToken());
            return ResponseEntity.ok(token);
        } catch (AuthException e) {
            throw new RuntimeException(e);
        }
    }
}
