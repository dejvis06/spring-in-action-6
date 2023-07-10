package tacos.web;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.Errors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tacos.domain.models.Ingredient;
import tacos.domain.models.Taco;
import tacos.domain.models.TacoOrder;
import tacos.web.dtos.TacoDTO;
import tacos.web.dtos.TacoOderDTO;

import static tacos.domain.models.Ingredient.*;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final WebClient webClient;

    public DesignTacoController(WebClient webClient) {
        this.webClient = webClient;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = this.webClient.get()
                .uri("/ingredients")
                .retrieve()
                .bodyToFlux(Ingredient.class)
                .collectList()
                .block();

        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
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

        if (errors.hasErrors()) {
            log.info("Errors: {}", errors);
            return "design";
        }

        TacoDTO dto = this.webClient.post()
                .uri("/tacos")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(createDTO(taco)), TacoOderDTO.class)
                .retrieve()
                .bodyToMono(TacoDTO.class)
                .block();
        taco.setId(dto.getId());

        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);

        return "redirect:/orders/current";
    }

    private static TacoDTO createDTO(Taco taco) {
        return TacoDTO.builder()
                .name(taco.getName())
                .ingredientIds(taco.getIngredients().stream().map(i -> Long.valueOf(i.getId())).collect(Collectors.toSet()))
                .build();
    }

    private Iterable<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}