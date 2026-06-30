package com.aa.recipe.finder.service;


import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aa.recipe.finder.connector.MealDbConnector;
import com.aa.recipe.finder.dao.Meal;
import com.aa.recipe.finder.dao.RecepieResponse;
import com.aa.recipe.finder.dao.RecipeDetails;

import reactor.core.publisher.Mono;

@Service
public class RecepieFinderService {

    private final MealDbConnector mealDbConnector;

    public RecepieFinderService(MealDbConnector mealDbConnector) {
        this.mealDbConnector = mealDbConnector;
    }

    public Mono<List<Meal>> getRecepies(String name) {
        return mealDbConnector.searchRecepiesByName(name)
                .flatMap(response -> Mono.just(mapResponseToResponse(response)));
    }

    public Mono<RecipeDetails> getRecepieDetails(String id) {
        return mealDbConnector.searchRecepiesById(id)
                .flatMap(response -> Mono.just(mapResponseToRecipe(response)));
    }

    private List<Meal> mapResponseToResponse(Map<String, Object> responseMap) {

        List<Map<String, Object>> meals = (List<Map<String, Object>>) responseMap.get("meals");
        if (meals.isEmpty()) {
            return Collections.emptyList();
        }
        return meals.stream()
                .map(this::mapResponseToMeal)
                .toList();
    }

    private Meal mapResponseToMeal(Map<String, Object> mealsMap) {
        return Meal.builder()
                .id((String) mealsMap.get("idMeal"))
                .name((String) mealsMap.get("strMeal"))
                .thumbnailUrl((String) mealsMap.get("strMealThumb"))
                .build();
    }

    private RecipeDetails mapResponseToRecipe(Map<String, Object> responseMap) {
        return RecipeDetails.builder()
                .id((String) responseMap.get("idMeal"))
                .name((String) responseMap.get("strMeal"))
                .thumbnailUrl((String) responseMap.get("strMealThumb"))
                .youtubeUrl((String) responseMap.get("strYoutube"))
                .instructions((String) responseMap.get("strInstructions"))
                .area((String) responseMap.get("strArea"))
                .category((String) responseMap.get("strCategory"))
                .tags((String) responseMap.get("strTags"))
                .thumbnailUrl((String) responseMap.get("strMealThumb"))
                //.ingredients()
                .build();
    }

}
