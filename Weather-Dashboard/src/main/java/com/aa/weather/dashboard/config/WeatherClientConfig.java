package com.aa.weather.dashboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WeatherClientConfig {

    @Bean("weatherApiClient")
    public WebClient weatherApiClient() {
        return WebClient.builder().build();
    }
}
