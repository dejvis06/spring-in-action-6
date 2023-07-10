package com.example.web;

import com.example.domain.entities.TacoOrder;
import com.example.domain.repositories.OrderRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/orders",
        produces = "application/json")
@Slf4j
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TacoOrder> postTaco(@RequestBody @Valid TacoOrder taco) {
        log.info("Received order: {}", taco);
        return orderRepository.save(taco);
    }
}
