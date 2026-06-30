package com.aa.weather.dashboard.service;

import com.aa.weather.dashboard.dto.WeatherResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class WeatherDashboardService {
    @Value("${openweathermap.api.units}")
    private String units;

    @Value("${openweathermap.api.api-key}")
    private String apiKey;

    @Value("${openweathermap.api.base-url}")
    private String baseUrl;

    @Autowired
    private WebClient weatherApiClient;

    public Mono<WeatherResponseDTO> getCurrentWeatherDetails(String city) {
        return weatherApiClient.get()
                .uri(baseUrl + "/data/2.5/weather?q={city}&appid={key}&units={metric}", city, apiKey, units)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                }).map(response -> transformResponseToDTO(response))
                .onErrorResume(e -> {
                    //FIX-ME log later
                    System.out.println(e);
                    return Mono.just(new WeatherResponseDTO());
                });

    }

    private WeatherResponseDTO transformResponseToDTO(Map<String, Object> response) {
        WeatherResponseDTO responseDTO = new WeatherResponseDTO();
        responseDTO.setCity((String)response.get("name"));
        List weather = (List) response.get("weather");
        if(Objects.nonNull(weather) && weather.size() > 0) {
            Map<String, Object> weatherObj = (Map<String, Object>) weather.getFirst();
            responseDTO.setDescription((String) weatherObj.get("description"));
            responseDTO.setIconUrl((String) weatherObj.get("icon"));
        }
        Map<String, Object> sysObj = (Map<String, Object>) response.get("sys");
        if(Objects.nonNull(sysObj)) {
            responseDTO.setCountry((String)sysObj.get("country"));
        }

        Map<String, Object> mainObj = (Map<String, Object>) response.get("main");
        if(Objects.nonNull(mainObj)) {
            Object temp = mainObj.get("temp");
            if(Objects.nonNull(temp)) {
                responseDTO.setTemperature(Double.valueOf(temp.toString()));
            }
            responseDTO.setFeelsLike(Double.valueOf(mainObj.get("feels_like").toString()));
            responseDTO.setHumidity(Long.valueOf(mainObj.get("humidity").toString()));
        }

        Map<String, Object> windObj = (Map<String, Object>) response.get("wind");
        if(Objects.nonNull(windObj)) {
            responseDTO.setWindSpeed(Double.valueOf(windObj.get("speed").toString()));
        }
        return responseDTO;
    }
}
