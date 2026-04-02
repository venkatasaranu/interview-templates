package com.aa.recipe.finder.service;

import com.aa.recipe.finder.dto.CategoryInfo;
import com.aa.recipe.finder.dto.RecipeDetail;
import com.aa.recipe.finder.dto.RecipeSummary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecipeService {

    private final WebClient webClient;

    public RecipeService(WebClient webClient) {
        this.webClient = webClient;
    }

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE =
            new ParameterizedTypeReference<>() {};

    public Flux<RecipeSummary> searchRecipes(String query) {
        return webClient.get()
                .uri("/search.php?s={query}", query)
                .retrieve()
                .bodyToMono(MAP_TYPE)
                .flatMapMany(this::parseMealsAsSummaries);
    }

    public Mono<RecipeDetail> getRecipeById(String mealId) {
        return webClient.get()
                .uri("/lookup.php?i={mealId}", mealId)
                .retrieve()
                .bodyToMono(MAP_TYPE)
                .flatMap(response -> {
                    List<Map<String, Object>> meals = extractMealsList(response);
                    if (meals == null || meals.isEmpty()) {
                        return Mono.empty();
                    }
                    return Mono.just(mapToRecipeDetail(meals.get(0)));
                });
    }

    public Mono<RecipeDetail> getRandomRecipe() {
        return webClient.get()
                .uri("/random.php")
                .retrieve()
                .bodyToMono(MAP_TYPE)
                .flatMap(response -> {
                    List<Map<String, Object>> meals = extractMealsList(response);
                    if (meals == null || meals.isEmpty()) {
                        return Mono.empty();
                    }
                    return Mono.just(mapToRecipeDetail(meals.get(0)));
                });
    }

    public Flux<RecipeSummary> getRecipesByCategory(String category) {
        return webClient.get()
                .uri("/filter.php?c={category}", category)
                .retrieve()
                .bodyToMono(MAP_TYPE)
                .flatMapMany(this::parseMealsAsSummaries);
    }

    public Flux<CategoryInfo> getCategories() {
        return webClient.get()
                .uri("/categories.php")
                .retrieve()
                .bodyToMono(MAP_TYPE)
                .flatMapMany(response -> {
                    if (response == null || response.get("categories") == null) {
                        return Flux.empty();
                    }

                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> categories = (List<Map<String, Object>>) response.get("categories");

                    return Flux.fromIterable(categories)
                            .map(cat -> {
                                CategoryInfo info = new CategoryInfo();
                                info.setId(getString(cat, "idCategory"));
                                info.setName(getString(cat, "strCategory"));
                                info.setThumbnailUrl(getString(cat, "strCategoryThumb"));
                                info.setDescription(getString(cat, "strCategoryDescription"));
                                return info;
                            });
                });
    }

    // --- Helper methods ---

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> extractMealsList(Map<String, Object> response) {
        if (response == null || response.get("meals") == null) {
            return null;
        }
        return (List<Map<String, Object>>) response.get("meals");
    }

    private Flux<RecipeSummary> parseMealsAsSummaries(Map<String, Object> response) {
        List<Map<String, Object>> meals = extractMealsList(response);
        if (meals == null) {
            return Flux.empty();
        }

        return Flux.fromIterable(meals)
                .map(meal -> {
                    RecipeSummary summary = new RecipeSummary();
                    summary.setId(getString(meal, "idMeal"));
                    summary.setName(getString(meal, "strMeal"));
                    summary.setThumbnailUrl(getString(meal, "strMealThumb"));
                    return summary;
                });
    }

    private RecipeDetail mapToRecipeDetail(Map<String, Object> meal) {
        RecipeDetail detail = new RecipeDetail();
        detail.setId(getString(meal, "idMeal"));
        detail.setName(getString(meal, "strMeal"));
        detail.setCategory(getString(meal, "strCategory"));
        detail.setArea(getString(meal, "strArea"));
        detail.setInstructions(getString(meal, "strInstructions"));
        detail.setThumbnailUrl(getString(meal, "strMealThumb"));
        detail.setYoutubeUrl(getString(meal, "strYoutube"));
        detail.setTags(getString(meal, "strTags"));

        Map<String, String> ingredients = new LinkedHashMap<>();
        for (int i = 1; i <= 20; i++) {
            String ingredient = getString(meal, "strIngredient" + i);
            String measure = getString(meal, "strMeasure" + i);
            if (ingredient != null && !ingredient.isBlank()) {
                ingredients.put(ingredient, measure != null ? measure.trim() : "");
            }
        }
        detail.setIngredients(ingredients);

        return detail;
    }

    private String getString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            return null;
        }
        String str = value.toString();
        return str.isEmpty() ? null : str;
    }
}
