package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.domain.entities.Ingredient;
import tacos.domain.entities.Ingredient.Type;
import tacos.domain.entities.User;
import tacos.domain.repositories.IngredientRepository;
import tacos.domain.repositories.UserRepository;


@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepository, UserRepository userRepository, PasswordEncoder encoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) {
                addIngredients(ingredientRepository);
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

    private static void addIngredients(IngredientRepository ingredientRepository) {
        ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
        ingredientRepository.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
        ingredientRepository.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
        ingredientRepository.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
        ingredientRepository.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
        ingredientRepository.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
        ingredientRepository.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
        ingredientRepository.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
        ingredientRepository.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
    }
}
