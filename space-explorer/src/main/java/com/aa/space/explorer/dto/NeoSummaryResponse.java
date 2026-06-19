package com.aa.space.explorer.dto;
/**
 * @author Ravanth Pasam
 *
 */
import lombok.Builder;

import java.util.List;

@Builder
public record NeoSummaryResponse(
        int totalCount,
        int hazardousCount,
        ClosestApproach closestApproach
) {
    @Builder
    public record ClosestApproach(
            String name,
            double distanceKm,
            double velocityKph,
            String date
    ) {}
}
