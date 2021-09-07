package Telegram;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")

public class AppProperty {
    public  List<String>mode;
    private List<String> NAME;
    private String TOKEN;
    private String DEFAULT_LOCALE;
    private String URL;

}

