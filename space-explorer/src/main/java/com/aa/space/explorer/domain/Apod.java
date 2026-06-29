package com.aa.space.explorer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Apod {
    String status;
    String date;
    String title;
    String explanation;
    String mediaType;
    String url;
    String hdUrl;
    String thumbnailUrl;
    String copyright;
}
