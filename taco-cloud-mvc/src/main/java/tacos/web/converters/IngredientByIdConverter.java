package tacos.web.converters;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.domain.entities.Ingredient;
import tacos.domain.entities.Ingredient.Type;


@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private Map<String, Ingredient> ingredientMap = new HashMap<>();

    public IngredientByIdConverter() {
        ingredientMap.put("1",
                new Ingredient("1", "Flour Tortilla", Type.WRAP));
        ingredientMap.put("2",
                new Ingredient("2", "Corn Tortilla", Type.WRAP));
        ingredientMap.put("3",
                new Ingredient("3", "Ground Beef", Type.PROTEIN));
        ingredientMap.put("4",
                new Ingredient("4", "Carnitas", Type.PROTEIN));
        ingredientMap.put("5",
                new Ingredient("5", "Diced Tomatoes", Type.VEGGIES));
        ingredientMap.put("6",
                new Ingredient("6", "Lettuce", Type.VEGGIES));
        ingredientMap.put("7",
                new Ingredient("7", "Cheddar", Type.CHEESE));
        ingredientMap.put("8",
                new Ingredient("8", "Monterrey Jack", Type.CHEESE));
        ingredientMap.put("9",
                new Ingredient("9", "Salsa", Type.SAUCE));
        ingredientMap.put("10",
                new Ingredient("10", "Sour Cream", Type.SAUCE));
    }

    @Override
    public Ingredient convert(String id) {
        return ingredientMap.get(id);
    }
}