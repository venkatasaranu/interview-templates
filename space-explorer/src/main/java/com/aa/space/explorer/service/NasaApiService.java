package com.aa.space.explorer.service;
/**
 * @author Ravanth Pasam
 *
 */
import com.aa.space.explorer.config.NasaApiProperties;
import com.aa.space.explorer.dto.*;
import com.aa.space.explorer.dto.nasa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NasaApiService {

    private final RestClient nasaRestClient;
    private final NasaApiProperties properties;

    public ApodResponse getApod(String date) {
        NasaApodDto dto = nasaRestClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/planetary/apod")
                            .queryParam("api_key", properties.getApiKey());
                    if (date != null) {
                        uriBuilder.queryParam("date", date);
                    }
                    return uriBuilder.build();
                })
                .retrieve()
                .body(NasaApodDto.class);

        return mapApod(dto);
    }

    public List<ApodResponse> getApodRange(String startDate, String endDate) {
        List<NasaApodDto> dtos = nasaRestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/planetary/apod")
                        .queryParam("api_key", properties.getApiKey())
                        .queryParam("start_date", startDate)
                        .queryParam("end_date", endDate)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return dtos.stream().map(this::mapApod).toList();
    }

    public NeoSummaryResponse getNeoSummary(String startDate, String endDate) {
        NasaNeoFeedDto dto = nasaRestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/neo/rest/v1/feed")
                        .queryParam("start_date", startDate)
                        .queryParam("end_date", endDate)
                        .queryParam("api_key", properties.getApiKey())
                        .build())
                .retrieve()
                .body(NasaNeoFeedDto.class);

        List<NasaNeoFeedDto.NearEarthObject> allNeos = dto.getNearEarthObjects().values().stream()
                .flatMap(List::stream)
                .toList();

        int hazardousCount = (int) allNeos.stream()
                .filter(NasaNeoFeedDto.NearEarthObject::isPotentiallyHazardous)
                .count();

        NeoSummaryResponse.ClosestApproach closest = allNeos.stream()
                .flatMap(neo -> neo.getCloseApproachData().stream()
                        .map(ca -> NeoSummaryResponse.ClosestApproach.builder()
                                .name(neo.getName())
                                .distanceKm(Double.parseDouble(ca.getMissDistance().getKilometers()))
                                .velocityKph(Double.parseDouble(ca.getRelativeVelocity().getKilometersPerHour()))
                                .date(ca.getCloseApproachDate())
                                .build()))
                .min(Comparator.comparingDouble(NeoSummaryResponse.ClosestApproach::distanceKm))
                .orElse(null);

        return NeoSummaryResponse.builder()
                .totalCount(allNeos.size())
                .hazardousCount(hazardousCount)
                .closestApproach(closest)
                .build();
    }
    private ApodResponse mapApod(NasaApodDto dto) {
        return ApodResponse.builder()
                .date(dto.getDate())
                .title(dto.getTitle())
                .explanation(dto.getExplanation())
                .mediaType(dto.getMediaType())
                .url(dto.getUrl())
                .hdUrl(dto.getHdUrl())
                .thumbnailUrl(dto.getThumbnailUrl())
                .copyright(dto.getCopyright())
                .build();
    }
}
