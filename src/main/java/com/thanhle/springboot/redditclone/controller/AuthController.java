package com.thanhle.springboot.redditclone.controller;

import com.thanhle.springboot.redditclone.dto.request.LoginRequest;
import com.thanhle.springboot.redditclone.dto.request.RefreshTokenRequest;
import com.thanhle.springboot.redditclone.dto.request.RegisterRequest;
import com.thanhle.springboot.redditclone.dto.response.AuthenticationResponse;
import com.thanhle.springboot.redditclone.service.AuthService;
import com.thanhle.springboot.redditclone.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest request) {
        authService.signup(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity("Account Activated Successully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!");
    }
}
