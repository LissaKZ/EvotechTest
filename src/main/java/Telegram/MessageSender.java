package Telegram;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class MessageSender {
    public static void sendMessage(Bot bot, Long chatId, String msg)  {
        try {
            bot.execute(new SendMessage(chatId, msg));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    static void sendMessage(Bot bot, Long chatId, String msg, InlineKeyboardMarkup keyboardMarkup)  {
        try {
            bot.execute(new SendMessage(chatId, msg).setReplyMarkup(keyboardMarkup));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(Bot bot,Long chatId, InlineKeyboardMarkup keyboardMarkup) {
        try {
            bot.execute(new SendMessage(chatId, "Выберите").setReplyMarkup(keyboardMarkup));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(Bot bot, Long chatId, SendMessage message) {
        message.setChatId(chatId);
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
