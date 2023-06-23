package com.example.domain.repositories;

import com.example.domain.entities.Taco;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TacoRepository
        extends JpaRepository<Taco, Long> {

}