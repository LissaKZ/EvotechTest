package Telegram;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendVoice;
import org.telegram.telegrambots.api.objects.*;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.FileNotFoundException;
import java.io.IOException;

public enum UpdateType {
    LIMIT{
        @Override
        public boolean match(Update update) {
            if(update.hasMessage()){
                try {
                    Integer.parseInt(update.getMessage().getText());
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            return false;
        }
        @Override
        public void handle(Bot bot, Update update) {
            super.handle(bot, update);
            UserManager.getUser(chatId).setLimit(Integer.parseInt(update.getMessage().getText()));
            MessageSender.sendMessage(bot,chatId,"Напишите отправляемые сообщения. Каждый диалог с новой строки, отправляемые сообщения через запятую.\nНапример:\n" +
                    "фраза1,фраза2,фраза3\n" +
                    "фраза4,фраза5\n" +
                    "Здесь фразы 1-3 будут отправлены в первом диалоге, фразы 4 и 5 в другом. Диалоги выбираются случайно");
        }
    },
    TXT_FILE{
        @Override
        public boolean match(Update update) {
            if(update.hasMessage()){
                return update.getMessage().hasDocument()&&update.getMessage().getDocument().getFileName().contains(".txt");
            }
            return false;
        }

        @Override
        public void handle(Bot bot, Update update) {
            super.handle(bot, update);
            try {
                if(UserManager.getUser(chatId).getMode()!=null&& UserManager.getUser(chatId).getMode().equals("1"))
                UserManager.getUser(chatId).setKeywords(bot.execute(new GetFile().setFileId(update.getMessage().getDocument().getFileId())));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            MessageSender.sendMessage(bot,chatId,"Введите целевые фразы");

        }
    },
    XLS_FILE{
        @Override
        public boolean match(Update update) {
            if(update.hasMessage()){
                return update.getMessage().hasDocument()&&update.getMessage().getDocument().getFileName().contains(".xls");
            }
            return false;
        }

        @Override
        public void handle(Bot bot, Update update) {
            super.handle(bot, update);

                if(UserManager.getUser(chatId).mode.equals("2")) {
                    try {
                        UserManager.getUser(chatId).setTestCase(bot.execute(new GetFile().setFileId(update.getMessage().getDocument().getFileId())));

                MessageSender.sendMessage(bot,chatId,"Для начала тестирования нажмите /test\n Для того чтобы остановить тестирование, нажмите /stop");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

        }
    },
    USER_PARAM{
        @Override
        public void init(Update update) {
            message = UpdateType.getMessage(update.getCallbackQuery());
            chatId = UpdateType.getChatID(update.getCallbackQuery());
        }

        @Override
        public boolean match(Update update) {
            if (update.hasCallbackQuery()) {
                return update.getCallbackQuery().getData() != null && update.getCallbackQuery().getData().startsWith("prop");
            }
            return false;
        }
        @Override
        public void handle(Bot bot, Update update) {
            init(update);
            String key=message.split(":")[0];
            String value=message.split(":")[1];
            UserManager.getUser(chatId).setParam(key, value);
            InlineKeyboardMarkup message=null;
            String text="Вы выбрали "+value;

            switch (key){
                case "prop.app":
                    message=Keyboards.Keyboard.MODES.getKeyboard(bot);
                    text=text+". Выберите режим";
                    break;
                case "prop.mode":
                    text=text+" режим.";
                    switch (UserManager.getUser(chatId).mode) {
                        case "1":
                            text = text + " Введите путь.";
                            break;
                        case "2":
                            text = text + " Отправьте тест кейс в формате .xls";
                            break;
                        case "3":
                            text=text+" Укажите количество диалогов";
                            break;
                    }
            }
            MessageSender.sendMessage(bot,chatId,new SendMessage().setReplyMarkup(message).setText(text));
        }
    },

    VOICE_MESSAGE {
        @Override
        public void handle(Bot bot, Update update) {
            init(update);
            Voice voice = update.getMessage().getVoice();
            SendVoice sendVoice = new SendVoice();
            sendVoice.setVoice(voice.getFileId());
            sendVoice.setChatId(chatId);
            try {
                bot.sendVoice(sendVoice);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean match(Update update) {
            if (update.hasMessage())
                return update.getMessage().getVoice() != null;
            return false;
        }
    },

    COMMAND {
        @Override
        public void handle(Bot bot, Update update) {
            init(update);
            for (Command command : Command.values()) {
                if (message.startsWith(command.getCommand())) {
                    command.executeCommand(bot, chatId,message);
                    return;
                }
            }

        }

        @Override
        public boolean match(Update update) {
            if (update.hasMessage())
                if (update.getMessage().getText() != null & update.getMessage().getEntities() != null)
                    return update.getMessage().getEntities().get(0).getType().equals("bot_command");
            return false;
        }
    },

    BUTTON {
        @Override
        public void init(Update update) {
            message = UpdateType.getMessage(update.getCallbackQuery());
            chatId = UpdateType.getChatID(update.getCallbackQuery());

        }


        @Override
        public boolean match(Update update) {
            if (update.hasCallbackQuery())
                return update.getCallbackQuery().getData() != null;
            return false;
        }

    },
    TARGET_MESSAGE{
        @Override
        public boolean match(Update update) {
            if (update.hasMessage())
                return UserManager.getUser(chatId).mode.equals("1")&update.getMessage().getText() != null & update.getMessage().getEntities() == null&UserManager.getUser(chatId).getPath()!=null&UserManager.getUser(chatId).getKeywords()!=null;
            return false;
        }

        @Override
        public void handle(Bot bot, Update update) {
            super.handle(bot, update);
            UserManager.getUser(chatId).setTarget(message);
            MessageSender.sendMessage(bot,chatId,"Для начала тестирования нажмите /test\n Для того чтобы остановить тестирование, нажмите /stop");
        }
    },
    KEYWORDS {
        @Override
        public boolean match(Update update) {
            if (update.hasMessage()) {
                return UserManager.getUser(chatId).mode.equals("1") & update.getMessage().getText() != null & update.getMessage().getEntities() == null & UserManager.getUser(chatId).getPath()!=null;

            }return false;
        }

        @Override
        public void handle(Bot bot, Update update) {
            super.handle(bot, update);
            UserManager.getUser(chatId).setKeywords(message);
            MessageSender.sendMessage(bot,chatId,"Введите целевые фразы");
        }
    },
    DIALOG{
        @Override
        public boolean match(Update update) {
            if (update.hasMessage()) {
                return UserManager.getUser(chatId).mode.equals("3") & update.getMessage().getText() != null;
            }return false;
        }

        @Override
        public void handle(Bot bot, Update update) {
            super.handle(bot, update);
            UserManager.getUser(chatId).setDialog(message);
            MessageSender.sendMessage(bot,chatId,"Для начала тестирования нажмите /test\n Для того чтобы остановить тестирование, нажмите /stop");
        }
    },
    PATH{
        @Override
        public boolean match(Update update) {
            if (update.hasMessage()) {
                return UserManager.getUser(chatId).mode.equals("1") & update.getMessage().getText() != null & update.getMessage().getEntities() == null;
            }
            return false;
        }

        @Override
        public void handle(Bot bot, Update update) {
            super.handle(bot, update);
            UserManager.getUser(chatId).setPath(message);
            MessageSender.sendMessage(bot,chatId,"Введите ключевые слова или отправьте их в файле .txt");
        }
    };

    private static Long chatId;
    private static String telegramLogin;
    private static String message;
    public void init(Update update) {
        telegramLogin= UpdateType.getTelegramLogin(update.getMessage());
        message= UpdateType.getMessage(update.getMessage());
        chatId= UpdateType.getChatID(update.getMessage());

    }

    public void handle(Bot bot, Update update) {
        init(update);

    }
    private static Long getChatID(Message message) {
        return message.getChat().getId();
    }

    private static Long getChatID(CallbackQuery callbackQuery) {
        return callbackQuery.getMessage().getChatId();
    }

    private static String getMessage(Message message) {
        return message.getText();
    }

    private static String getTelegramLogin(Message message) {
        String userName = message.getFrom().getUserName();
        if (userName != null)
            return userName;
        else
            return "N/A";
    }

    private static String getMessage(CallbackQuery callbackQuery) {
        return callbackQuery.getData();
    }
    public abstract boolean match(Update update);
}
