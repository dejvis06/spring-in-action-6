package com.example.domain.entities;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Ingredient {

    @Id
    private Long id;
    private String name;
    private Type type;

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}