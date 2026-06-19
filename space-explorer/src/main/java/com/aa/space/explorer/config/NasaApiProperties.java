package com.aa.space.explorer.config;
/**
 * @author Ravanth Pasam
 *
 */
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "nasa.api")
public class NasaApiProperties {
    private String baseUrl;
    private String apiKey;
}
