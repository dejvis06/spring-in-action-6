package com.example;

import com.example.domain.entities.Ingredient;
import com.example.domain.entities.Ingredient.Type;
import com.example.domain.repositories.IngredientRepository;
import com.example.domain.repositories.TacoRepository;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class TacoCloudRestfulApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudRestfulApiApplication.class, args);
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

        return initializer;
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) {
                addIngredientsAndTacos(ingredientRepository, tacoRepository);
            }
        };
    }

    private static void addIngredientsAndTacos(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        Ingredient flourTortilla = new Ingredient(
                null, "Flour Tortilla", Type.WRAP);
        Ingredient cornTortilla = new Ingredient(
                null, "Corn Tortilla", Type.WRAP);
        Ingredient groundBeef = new Ingredient(
                null, "Ground Beef", Type.PROTEIN);
        Ingredient carnitas = new Ingredient(
                null, "Carnitas", Type.PROTEIN);
        Ingredient tomatoes = new Ingredient(
                null, "Diced Tomatoes", Type.VEGGIES);
        Ingredient lettuce = new Ingredient(
                null, "Lettuce", Type.VEGGIES);
        Ingredient cheddar = new Ingredient(
                null, "Cheddar", Type.CHEESE);
        Ingredient jack = new Ingredient(
                null, "Monterrey Jack", Type.CHEESE);
        Ingredient salsa = new Ingredient(
                null, "Salsa", Type.SAUCE);
        Ingredient sourCream = new Ingredient(
                null, "Sour Cream", Type.SAUCE);

        ingredientRepository.saveAll(Arrays.asList(
                        flourTortilla, cornTortilla, groundBeef, carnitas, tomatoes, lettuce, cheddar, jack, salsa, sourCream))
                .subscribe(ingredient -> {
                    log.info("Created: {}", ingredient);
                });
    }
}
