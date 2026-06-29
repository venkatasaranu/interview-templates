package com.aa.recipe.finder.mapper;

import com.aa.recipe.finder.connector.RecipeResponseDto;
import com.aa.recipe.finder.dto.RecipeResponse;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {

    public RecipeResponse mapRecipeResponse(RecipeResponseDto.MealDb mealDb) {
        return new RecipeResponse(
                mealDb.id(),
                mealDb.name(),
                mealDb.thumbnailUrl()
        );
    }
}
