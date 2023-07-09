package com.example.domain.repositories;

import com.example.domain.entities.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    User findByUsername(String username);
}
