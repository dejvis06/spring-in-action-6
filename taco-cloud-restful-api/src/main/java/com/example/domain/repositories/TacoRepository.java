package com.example.domain.repositories;

import com.example.domain.entities.Taco;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {
}