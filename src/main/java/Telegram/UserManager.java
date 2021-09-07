package Telegram;

import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;


public class UserManager extends TimerTask {
    public static List<User> users=new ArrayList<>();

    private static final Integer BOT_MILIS_TO_TIMEOUT = 50000;

    private LocalDateTime lastUpdate;
    private Bot bot;
    private final Object lock = new Object();

    public UserManager(Bot bot) {
        this.bot=bot;
    }


    public static User getUser(Long chatId) {
        for (User user: users
             ) {
            if(user.chatId.equals(chatId)){
                return user;
            }
        }
        return null;
    }

    public static void addUser(Long chatId) {
        users.add(new User(chatId));
    }

    @SneakyThrows
    public void run(){
        manageActiveChats();
    }

    private void manageActiveChats() {
        synchronized (lock) {
            for (Map.Entry<Long, User> longLocalDateTimeEntry : Bot.activeChats.entrySet()) {
                lastUpdate = longLocalDateTimeEntry.getValue().getLastUpdate();
                if (App.getTime().isAfter(lastUpdate.plusSeconds(BOT_MILIS_TO_TIMEOUT / 1000))){

                }
            }
        }
    }
}
