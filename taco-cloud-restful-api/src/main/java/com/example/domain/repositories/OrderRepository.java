package com.example.domain.repositories;

import com.example.domain.entities.TacoOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<TacoOrder, Long> {
}