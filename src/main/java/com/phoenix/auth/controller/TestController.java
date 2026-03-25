package com.phoenix.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phoenix.auth.model.User;
import com.phoenix.auth.repository.UserRepository;

import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/auth")
public class TestController {

    private final UserRepository userRepository;

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/save")
    public Mono<User> saveUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}

