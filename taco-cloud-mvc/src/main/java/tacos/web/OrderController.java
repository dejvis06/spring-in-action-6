package tacos.web;

import jakarta.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tacos.domain.entities.TacoOrder;
import tacos.web.dtos.TacoOderDTO;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

    private final WebClient webClient;

    public OrderController(WebClient webClient) {
        this.webClient = webClient;
    }


    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors,
                               SessionStatus sessionStatus, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        order.setUser(authorizedClient.getPrincipalName());

        Mono<TacoOderDTO> response = this.webClient.post()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(createDTO(order)), TacoOderDTO.class)
                .retrieve()
                .bodyToMono(TacoOderDTO.class);

        response.subscribe(tacoOrder -> {
                    assert tacoOrder != null;
                },
                errors1 -> {
                    assert errors1 != null;
                }
        );

        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();

        return "redirect:/";
    }

    private TacoOderDTO createDTO(TacoOrder order) {
        TacoOderDTO dto = TacoOderDTO.builder()
                .deliveryName(order.getDeliveryName())
                .deliveryStreet(order.getDeliveryStreet())
                .deliveryCity(order.getDeliveryCity())
                .deliveryState(order.getDeliveryState())
                .deliveryZip(order.getDeliveryZip())
                .ccNumber(order.getCcNumber())
                .ccExpiration(order.getCcExpiration())
                .ccCVV(order.getCcCVV())
                .username(order.getUser())
                .build();
        order.getTacos().forEach(taco -> dto.addTaco(taco.getId()));
        return dto;
    }
}