package com.aa.recipe.finder.controller;

import com.aa.recipe.finder.dto.RecipeResponse;
import com.aa.recipe.finder.service.RecipeService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/search")
    public Flux<RecipeResponse> searchRecipeByName(@RequestParam("q") @NotNull String recipeName) {
        return recipeService.getRecipeByName(recipeName);
    }

    @GetMapping("/{mealId}")
    public Mono<RecipeSummaryResponse> serachRecipeByMealId(@PathVariable("mealId") @NotNull String mealId) {
        return recipeService.getRecipeByMealId(mealId);
    }
}
