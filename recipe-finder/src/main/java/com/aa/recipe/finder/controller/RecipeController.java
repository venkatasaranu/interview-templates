package com.aa.recipe.finder.controller;

import com.aa.recipe.finder.dto.CategoryInfo;
import com.aa.recipe.finder.dto.RecipeDetail;
import com.aa.recipe.finder.dto.RecipeSummary;
import com.aa.recipe.finder.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/api/recipes/search")
    public Flux<RecipeSummary> searchRecipes(@RequestParam("q") String query) {
        return recipeService.searchRecipes(query);
    }

    @GetMapping("/api/recipes/random")
    public Mono<ResponseEntity<RecipeDetail>> getRandomRecipe() {
        return recipeService.getRandomRecipe()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/api/recipes/category/{category}")
    public Flux<RecipeSummary> getRecipesByCategory(@PathVariable String category) {
        return recipeService.getRecipesByCategory(category);
    }

    @GetMapping("/api/recipes/{mealId}")
    public Mono<ResponseEntity<RecipeDetail>> getRecipeById(@PathVariable String mealId) {
        return recipeService.getRecipeById(mealId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/api/categories")
    public Flux<CategoryInfo> getCategories() {
        return recipeService.getCategories();
    }
}
