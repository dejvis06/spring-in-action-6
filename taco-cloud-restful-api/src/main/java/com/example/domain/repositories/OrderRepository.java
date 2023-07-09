package com.example.domain.repositories;

import com.example.domain.entities.TacoOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveCrudRepository<TacoOrder, Long> {
}