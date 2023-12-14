package ctjournal.telegrambot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "bot")
public class BotConfig {

    private String name;

    @Value("${token}")
    private String token;

    private long creatorId;
}