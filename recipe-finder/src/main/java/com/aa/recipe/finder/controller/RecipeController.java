package com.aa.recipe.finder.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aa.recipe.finder.dao.Meal;
import com.aa.recipe.finder.dao.RecipeDetails;
import com.aa.recipe.finder.service.RecepieFinderService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class RecipeController {

    private final RecepieFinderService recepieFinderService;

    public RecipeController(RecepieFinderService recepieFinderService) {
        this.recepieFinderService = recepieFinderService;
    }

    @GetMapping("/recipes/search")
    public Mono<List<Meal>> searchRecipe(@RequestParam String q) {
        return recepieFinderService.getRecepies(q);
    }

    @GetMapping("/recipes/{mealId}")
    public Mono<RecipeDetails> getRecipeDetails(@PathVariable String mealId) {
        return recepieFinderService.getRecepieDetails(mealId);
    }
}
