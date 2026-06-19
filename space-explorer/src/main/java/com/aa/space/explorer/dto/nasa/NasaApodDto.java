package com.aa.space.explorer.dto.nasa;
/**
 * @author Ravanth Pasam
 *
 */
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NasaApodDto {
    private String date;
    private String title;
    private String explanation;
    @JsonProperty("media_type")
    private String mediaType;
    private String url;
    @JsonProperty("hdurl")
    private String hdUrl;
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;
    private String copyright;
}
