package com.phoenix.auth.service;

import com.phoenix.auth.dto.AuthResponse;
import com.phoenix.auth.dto.LoginRequest;
import com.phoenix.auth.dto.RegisterRequest;
import com.phoenix.auth.model.User;
import com.phoenix.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Mono;
import com.phoenix.auth.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final UserRepository userRepository;
        private final BCryptPasswordEncoder passwordEncoder;
        private final JwtUtil jwtUtil;

        public Mono<String> register(RegisterRequest request) {

                return userRepository.findByEmail(request.getEmail())
                                .flatMap(existingUser -> Mono.<String>error(new ResponseStatusException(
                                                HttpStatus.CONFLICT, "User already exists")))
                                .switchIfEmpty(
                                                Mono.defer(() -> {
                                                        User user = User.builder()
                                                                        .email(request.getEmail())
                                                                        .password(passwordEncoder
                                                                                        .encode(request.getPassword()))
                                                                        .role("USER")
                                                                        .build();

                                                        return userRepository.save(user)
                                                                        .thenReturn("User registered successfully");
                                                }));
        }

        public Mono<AuthResponse> login(LoginRequest request) {

                return userRepository.findByEmail(request.getEmail())
                                .switchIfEmpty(Mono.error(
                                                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")))
                                .flatMap(user -> {
                                        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                                                return Mono.error(
                                                                new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                                                                                "Invalid password"));
                                        }

                                        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

                                        return Mono.just(new AuthResponse(token));
                                });
        }
}