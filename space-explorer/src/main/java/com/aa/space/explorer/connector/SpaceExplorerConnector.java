package com.aa.space.explorer.connector;

import com.aa.space.explorer.domain.Apod;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class SpaceExplorerConnector {
    private final WebClient spaceExplorerWebClient;

    public SpaceExplorerConnector(WebClient spaceExplorerWebClient) {
        this.spaceExplorerWebClient = spaceExplorerWebClient;
    }

    public Mono<Apod> getApod(String date) {
        return spaceExplorerWebClient.get()
                .uri(String.format("/planetary/apod?api_key=%s&date=%s", "DEMO_KEY", date))
                .retrieve()
                .bodyToMono(Apod.class);
    }

    public Mono<List<Apod>> getApodRange(String startDate, String endDate) {
        return spaceExplorerWebClient.get()
                .uri(String.format("/planetary/apod?api_key=%s&start_date=%s&end_date=%s", "DEMO_KEY", startDate, endDate))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Apod>>() {});
    }
}
