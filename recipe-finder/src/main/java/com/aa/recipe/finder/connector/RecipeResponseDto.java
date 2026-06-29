package com.aa.recipe.finder.connector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RecipeResponseDto(
        @JsonProperty("meals") List<MealDb> meals
) {
    public record MealDb(
            @JsonProperty("idMeal") String id,
            @JsonProperty("strMeal") String name,
            @JsonProperty("strMealThumb") String thumbnailUrl
    ){

    }
}
