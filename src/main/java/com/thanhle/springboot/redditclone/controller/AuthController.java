package com.thanhle.springboot.redditclone.controller;

import com.thanhle.springboot.redditclone.dto.request.LoginRequest;
import com.thanhle.springboot.redditclone.dto.request.RegisterRequest;
import com.thanhle.springboot.redditclone.dto.response.AuthenticationResponse;
import com.thanhle.springboot.redditclone.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
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
}
