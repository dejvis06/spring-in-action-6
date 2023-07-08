package tacos.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import tacos.domain.entities.Ingredient;
import tacos.domain.entities.Ingredient.Type;
import tacos.domain.entities.Taco;
import tacos.domain.entities.TacoOrder;

import jakarta.validation.Valid;
import tacos.domain.repositories.IngredientRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {


    final WebClient webClient;
    final IngredientRepository ingredientRepository;

    public DesignTacoController(WebClient webClient, IngredientRepository ingredientRepository) {
        this.webClient = webClient;
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model, @RegisteredOAuth2AuthorizedClient("taco-mvc") OAuth2AuthorizedClient authorizedClient) {
        List<Ingredient> ingredients = ingredientRepository.findAll();

        Flux<Ingredient> ingredientsFlux = this.webClient.get()
                .uri("/ingredients")
                .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToFlux(Ingredient.class);

        ingredientsFlux.collectList().subscribe(ingredients1 -> {
                    assert ingredients1.size() == 0;
                },
                error -> {

                });
        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(
            @Valid Taco taco, Errors errors,
            @ModelAttribute TacoOrder tacoOrder) {
        log.info("Errors: {}", errors);
        if (errors.hasErrors()) {
            return "design";
        }

        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);

        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}