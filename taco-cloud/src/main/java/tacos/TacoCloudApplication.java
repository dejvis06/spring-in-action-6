package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.domain.entities.Ingredient;
import tacos.domain.entities.Ingredient.Type;
import tacos.domain.entities.Taco;
import tacos.domain.entities.User;
import tacos.domain.repositories.IngredientRepository;
import tacos.domain.repositories.TacoRepository;
import tacos.domain.repositories.UserRepository;

import java.util.Arrays;


@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepository, TacoRepository tacoRepository, UserRepository userRepository, PasswordEncoder encoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) {
                addIngredientsAndTacos(ingredientRepository, tacoRepository);
                addUser(userRepository, encoder.encode("password"));
            }
        };
    }

    private static void addUser(UserRepository userRepository, String password) {
        userRepository.save(User.builder()
                .username("buzz")
                .password(password)
                .build());
    }

    private static void addIngredientsAndTacos(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        Ingredient flourTortilla = new Ingredient(
                "FLTO", "Flour Tortilla", Type.WRAP);
        Ingredient cornTortilla = new Ingredient(
                "COTO", "Corn Tortilla", Type.WRAP);
        Ingredient groundBeef = new Ingredient(
                "GRBF", "Ground Beef", Type.PROTEIN);
        Ingredient carnitas = new Ingredient(
                "CARN", "Carnitas", Type.PROTEIN);
        Ingredient tomatoes = new Ingredient(
                "TMTO", "Diced Tomatoes", Type.VEGGIES);
        Ingredient lettuce = new Ingredient(
                "LETC", "Lettuce", Type.VEGGIES);
        Ingredient cheddar = new Ingredient(
                "CHED", "Cheddar", Type.CHEESE);
        Ingredient jack = new Ingredient(
                "JACK", "Monterrey Jack", Type.CHEESE);
        Ingredient salsa = new Ingredient(
                "SLSA", "Salsa", Type.SAUCE);
        Ingredient sourCream = new Ingredient(
                "SRCR", "Sour Cream", Type.SAUCE);

        ingredientRepository.save(flourTortilla);
        ingredientRepository.save(cornTortilla);
        ingredientRepository.save(groundBeef);
        ingredientRepository.save(carnitas);
        ingredientRepository.save(tomatoes);
        ingredientRepository.save(lettuce);
        ingredientRepository.save(cheddar);
        ingredientRepository.save(jack);
        ingredientRepository.save(salsa);
        ingredientRepository.save(sourCream);

        Taco taco1 = new Taco();
        taco1.setName("Carnivore");
        taco1.setIngredients(Arrays.asList(
                flourTortilla, groundBeef, carnitas,
                sourCream, salsa, cheddar));
        tacoRepository.save(taco1);
        Taco taco2 = new Taco();
        taco2.setName("Bovine Bounty");
        taco2.setIngredients(Arrays.asList(
                cornTortilla, groundBeef, cheddar,
                jack, sourCream));
        tacoRepository.save(taco2);
        Taco taco3 = new Taco();
        taco3.setName("Veg-Out");
        taco3.setIngredients(Arrays.asList(
                flourTortilla, cornTortilla, tomatoes,
                lettuce, salsa));
        tacoRepository.save(taco3);
    }
}