package com.aa.recipe.finder.service;

import com.aa.recipe.finder.connector.RecipeWebClient;
import com.aa.recipe.finder.dto.RecipeResponse;
import com.aa.recipe.finder.mapper.RecipeMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeWebClient recipeWebClient;
    private final RecipeMapper recipeMapper;

    public Flux<RecipeResponse> getRecipeByName(@NotNull String recipeName) {
        return recipeWebClient.getRecipeByName(recipeName)
                .flatMapMany(recipeResponseDto ->
                        Flux.fromIterable(Optional.ofNullable(recipeResponseDto.meals())
                                .orElseGet(List::of)))
                .map(recipeMapper::mapRecipeResponse);

    }
}
