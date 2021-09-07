package Telegram;

import omilia.action.OmEndDialog;
import omilia.action.OmNoInput;
import omilia.action.OmSendMessage;
import omilia.response.OmResponse;
import omilia.utils.OmiliaDialogHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.*;

@Controller
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private BotProperties botProperty;

    @Autowired
    public AppProperty appProperty;

    @Autowired
    public Bot(AppProperty appProperty, BotProperties botProperties) {
        this.appProperty = appProperty;
        this.botProperty = botProperties;
    }

    public static final Map<Long, OmiliaDialogHandler> omiliaDialogs = new LinkedHashMap<>();

    public volatile static HashMap<Long, User> activeChats = new HashMap<>();
    private String botName;
    public static String botToken;

    void botConnect() throws InterruptedException {
        botName = botProperty.getBotName();
        botToken = botProperty.getBotToken();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiRequestException e) {
            int RECONNECT_PAUSE = 10000;
            Thread.sleep(RECONNECT_PAUSE);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        for (UpdateType updateType : UpdateType.values()) {
            if (updateType.match(update)) {
                updateType.handle(this, update);
                break;
            }
        }
    }


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void parseResponse(Long chatId, OmResponse response) {
    }

    public void sendOmiliaToTelegram(Long chatId, OmResponse response) {
    }


    List<OmResponse> sendToOmilia(OmiliaDialogHandler handler, Long chatId, String message) {
        try {
            return sendNoInput(handler,chatId, handler.execute(this,new OmSendMessage(UserManager.getUser(chatId).getApp(), message)));
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return null;
    }

    private List<OmResponse> sendNoInput(OmiliaDialogHandler handler, Long chatId, OmResponse response) throws IOException {
        List<OmResponse> responses=new ArrayList<>();
        if (response != null) {
            if (response.getAction() != null) {
                responses.add(response);
                if(!response.getAction().getType().equals("EXIT")) {
                    try {
                        while (response.getAction().getFieldsToElicit() == null) {
                            response = handler.execute(this, new OmNoInput(UserManager.getUser(chatId).getApp()));
                            responses.add(response);
                        }
                    }catch (NullPointerException e){
                        System.out.println(e);
                    }
                }
            }
        }
        return responses;
    }
}
