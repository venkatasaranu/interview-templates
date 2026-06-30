package com.aa.recipe.finder.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Meal {
    private String id;
    private String name;
    private String thumbnailUrl;
}
