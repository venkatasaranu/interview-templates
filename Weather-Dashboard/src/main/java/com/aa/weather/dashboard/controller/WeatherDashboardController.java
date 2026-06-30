package com.aa.weather.dashboard.controller;

import com.aa.weather.dashboard.dto.WeatherResponseDTO;
import com.aa.weather.dashboard.service.WeatherDashboardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController()
public class WeatherDashboardController {

    @Autowired
    private WeatherDashboardService weatherDashboardService;

    @GetMapping("/api/weather/current")
    public Mono<ResponseEntity<WeatherResponseDTO>> getCurrentWeatherDetails(@RequestParam("city") String city) {
        return this.weatherDashboardService.getCurrentWeatherDetails(city)
                .map(weatherResponseDTO -> {
                    if (StringUtils.isBlank(weatherResponseDTO.getCity())) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                    }
                    return ResponseEntity.ok(weatherResponseDTO);
                });
    }
}
