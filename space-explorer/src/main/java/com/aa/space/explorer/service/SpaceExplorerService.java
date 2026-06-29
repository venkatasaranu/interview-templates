package com.aa.space.explorer.service;

import com.aa.space.explorer.connector.SpaceExplorerConnector;
import com.aa.space.explorer.domain.Apod;
import com.aa.space.explorer.exceptionhandling.ApodRequestException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SpaceExplorerService {
    private final SpaceExplorerConnector spaceExplorerConnector;

    public SpaceExplorerService(SpaceExplorerConnector spaceExplorerConnector) {
        this.spaceExplorerConnector = spaceExplorerConnector;
    }

    public Mono<Apod> getApod(String date) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (localDate.isAfter(LocalDate.now())) {
                throw new ApodRequestException("Date cannot be in the future.");
            }
        } catch (ApodRequestException apodRequestException) {
            throw apodRequestException;
        } catch (Exception e) {
            throw new ApodRequestException("Invalid date format. Please use yyyy-MM-dd.");
        }
        return spaceExplorerConnector.getApod(date);
    }

    public Mono<List<Apod>> getApodRange(String startDate, String endDate) {
        try {
            LocalDate startLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (startLocalDate.isAfter(LocalDate.now()) || endLocalDate.isAfter(LocalDate.now())) {
                throw new ApodRequestException("Date cannot be in the future.");
            }
        } catch (ApodRequestException apodRequestException) {
            throw apodRequestException;
        } catch (Exception e) {
            throw new ApodRequestException("Invalid date format. Please use yyyy-MM-dd.");
        }
        return spaceExplorerConnector.getApodRange(startDate, endDate);
    }
}
