package com.aa.space.explorer.dto.nasa;
/**
 * @author Ravanth Pasam
 *
 */
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class NasaNeoFeedDto {
    @JsonProperty("element_count")
    private int elementCount;
    @JsonProperty("near_earth_objects")
    private Map<String, List<NearEarthObject>> nearEarthObjects;

    @Data
    public static class NearEarthObject {
        private String id;
        private String name;
        @JsonProperty("is_potentially_hazardous_asteroid")
        private boolean potentiallyHazardous;
        @JsonProperty("close_approach_data")
        private List<CloseApproach> closeApproachData;
    }

    @Data
    public static class CloseApproach {
        @JsonProperty("close_approach_date")
        private String closeApproachDate;
        @JsonProperty("relative_velocity")
        private RelativeVelocity relativeVelocity;
        @JsonProperty("miss_distance")
        private MissDistance missDistance;
    }

    @Data
    public static class RelativeVelocity {
        @JsonProperty("kilometers_per_hour")
        private String kilometersPerHour;
    }

    @Data
    public static class MissDistance {
        private String kilometers;
    }
}
