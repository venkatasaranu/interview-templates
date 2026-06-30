package com.aa.weather.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherResponseDTO {
    private String city;
    private String country;
    private Double temperature;
    private Double feelsLike;
    private Long humidity;
    private Double windSpeed;
    private String description;
    private String iconUrl;
}
