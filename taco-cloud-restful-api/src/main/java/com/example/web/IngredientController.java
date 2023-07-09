package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.domain.entities.Ingredient;
import com.example.domain.repositories.IngredientRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/ingredients", produces = "application/json")
public class IngredientController {
    private IngredientRepository repository;

    @Autowired
    public IngredientController(IngredientRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Flux<Ingredient> allIngredients() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Ingredient> saveIngredient(@RequestBody Ingredient ingredient) {
        return repository.save(ingredient);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable("id") String ingredientId) {
        repository.deleteById(ingredientId);
    }
}