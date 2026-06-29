package com.aa.recipe.finder.connector;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RecipeWebClient {
    private final WebClient webClient;

    public Mono<RecipeResponseDto> getRecipeByName(String query){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search.php")
                        .queryParam("s", query)
                        .build())
                .retrieve()
                .bodyToMono(RecipeResponseDto.class);

    }
}
