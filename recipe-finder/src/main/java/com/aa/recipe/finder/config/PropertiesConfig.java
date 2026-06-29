package com.aa.recipe.finder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "themealdb.api")
public record PropertiesConfig(
        String baseUrl
) {
}
