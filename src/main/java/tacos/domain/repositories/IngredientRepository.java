package tacos.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import tacos.domain.entities.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}