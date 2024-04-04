package org.example.boboshortner.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "short-url")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlSettings {

    private String baseUrl;
    private int length;
}
