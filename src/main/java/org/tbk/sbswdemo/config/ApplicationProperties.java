package org.tbk.sbswdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {
    private String name;
    private String description;
}
