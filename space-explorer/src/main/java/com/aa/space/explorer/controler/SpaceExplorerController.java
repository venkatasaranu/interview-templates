package com.aa.space.explorer.controler;
/**
 * @author Ravanth Pasam
 *
 */
import com.aa.space.explorer.dto.*;
import com.aa.space.explorer.service.NasaApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Space Explorer", description = "NASA Open API integration")
public class SpaceExplorerController {

    private static final Set<String> VALID_ROVERS = Set.of("curiosity", "opportunity", "spirit");

    private final NasaApiService nasaApiService;

    @GetMapping("/apod")
    @Operation(summary = "Astronomy Picture of the Day")
    public ResponseEntity<ApodResponse> getApod(@RequestParam(required = false) String date) {
        if (date != null) {
            validateDateFormat(date);
        }
        return ResponseEntity.ok(nasaApiService.getApod(date));
    }

    @GetMapping("/apod/range")
    @Operation(summary = "APOD date range")
    public ResponseEntity<List<ApodResponse>> getApodRange(@RequestParam String startDate,
                                                           @RequestParam String endDate) {
        LocalDate start = parseDate(startDate);
        LocalDate end = parseDate(endDate);
        if (ChronoUnit.DAYS.between(start, end) > 30) {
            throw new IllegalArgumentException("Date range must not exceed 30 days");
        }
        return ResponseEntity.ok(nasaApiService.getApodRange(startDate, endDate));
    }

    @GetMapping("/neo")
    @Operation(summary = "Near-Earth Objects summary")
    public ResponseEntity<NeoSummaryResponse> getNeoSummary(@RequestParam String startDate,
                                                             @RequestParam String endDate) {
        LocalDate start = parseDate(startDate);
        LocalDate end = parseDate(endDate);
        if (ChronoUnit.DAYS.between(start, end) > 7) {
            throw new IllegalArgumentException("Date range must not exceed 7 days");
        }
        return ResponseEntity.ok(nasaApiService.getNeoSummary(startDate, endDate));
    }
    private void validateDateFormat(String date) {
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        }
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        }
    }
}
