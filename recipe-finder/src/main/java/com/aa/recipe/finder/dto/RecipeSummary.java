package com.aa.recipe.finder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeSummary {
    private String id;
    private String name;
    private String thumbnailUrl;
}
