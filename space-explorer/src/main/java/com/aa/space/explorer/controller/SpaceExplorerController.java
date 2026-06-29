package com.aa.space.explorer.controller;

import com.aa.space.explorer.connector.SpaceExplorerConnector;
import com.aa.space.explorer.domain.Apod;
import com.aa.space.explorer.service.SpaceExplorerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SpaceExplorerController {
    private final SpaceExplorerService spaceExplorerService;

    public SpaceExplorerController(SpaceExplorerService spaceExplorerService) {
        this.spaceExplorerService = spaceExplorerService;
    }

    @GetMapping("/apod")
    public Mono<Apod> getApod(@RequestParam String date) {
        return spaceExplorerService.getApod(date);
    }

    @GetMapping("/apod/range")
    public Mono<List<Apod>> getApodRange(@RequestParam String startDate, @RequestParam String endDate) {
        return spaceExplorerService.getApodRange(startDate, endDate);
    }
}
