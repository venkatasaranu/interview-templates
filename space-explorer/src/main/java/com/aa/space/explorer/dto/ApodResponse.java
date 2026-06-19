package com.aa.space.explorer.dto;
/**
 * @author Ravanth Pasam
 *
 */
import lombok.Builder;

@Builder
public record ApodResponse(
        String date,
        String title,
        String explanation,
        String mediaType,
        String url,
        String hdUrl,
        String thumbnailUrl,
        String copyright
) {}
