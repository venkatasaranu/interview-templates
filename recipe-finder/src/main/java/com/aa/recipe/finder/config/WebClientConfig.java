package com.aa.recipe.finder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${themealdb.api.base-url}")
    private String recipeApiBaseUrl;

    @Bean("recipeApiWebClient")
    public WebClient recipeApiWebClient() {
        return WebClient.builder()
                .baseUrl(recipeApiBaseUrl)
                .build();
    }
}
