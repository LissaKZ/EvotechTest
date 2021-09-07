package Telegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.telegram.telegrambots.ApiContextInitializer;
import java.time.LocalDateTime;

@EnableAsync
@SpringBootApplication
public class App {
    public static void main(String[] args) throws InterruptedException {
        ApiContextInitializer.init();
        Bot bot=SpringApplication.run(App.class, args).getBean(Bot.class);
        bot.botConnect();
    }

    public static LocalDateTime getTime() {
        return LocalDateTime.now();
    }
}

