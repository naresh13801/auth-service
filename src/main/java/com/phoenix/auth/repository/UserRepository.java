package com.phoenix.auth.repository;

import com.phoenix.auth.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Mono<User> findByEmail(String email);
}
