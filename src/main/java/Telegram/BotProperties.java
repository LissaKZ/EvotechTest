package Telegram;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Getter
@Setter
@Validated
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "bot")
public class BotProperties {
    private String botName;
    private String botToken;
    private String resFilePath;
}
