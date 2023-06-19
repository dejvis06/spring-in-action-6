package tacos.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tacos.domain.entities.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, String> {
}