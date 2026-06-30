package com.aa.recipe.finder.connector;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import com.aa.recipe.finder.config.WebClientConfig;

import reactor.core.publisher.Mono;

@Component
public class MealDbConnector {

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_RESPONSE = new ParameterizedTypeReference<>() {};

    private final WebClientConfig webClientConfig;

    public MealDbConnector(WebClientConfig webClientConfig) {
        this.webClientConfig = webClientConfig;
    }


    public Mono<Map<String, Object>> searchRecepiesByName(String name) {
        return webClientConfig.recipeApiWebClient()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search.php")
                        .queryParam("s", name)
                        .build())
                .retrieve()
                .bodyToMono(MAP_RESPONSE);
    }

    public Mono<Map<String, Object>> searchRecepiesById(String id) {
        return webClientConfig.recipeApiWebClient()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/lookup.php")
                        .queryParam("i", id)
                        .build())
                .retrieve()
                .bodyToMono(MAP_RESPONSE);
    }
}
