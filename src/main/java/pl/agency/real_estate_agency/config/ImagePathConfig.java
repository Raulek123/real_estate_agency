package pl.agency.real_estate_agency.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "path")
@Component
@Data
public class ImagePathConfig {
    private String imageSave;
    private String imageGet;
}
