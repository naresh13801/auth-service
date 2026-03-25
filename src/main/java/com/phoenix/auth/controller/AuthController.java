package com.phoenix.auth.controller;

import com.phoenix.auth.dto.AuthResponse;
import com.phoenix.auth.dto.LoginRequest;
import com.phoenix.auth.dto.RegisterRequest;
import com.phoenix.auth.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public Mono<String> register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public Mono<AuthResponse> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/profile")
    public Mono<String> profile(Authentication authentication) {
        return Mono.just("Logged in user: " + authentication.getName());
    }
}