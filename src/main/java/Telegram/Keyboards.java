package Telegram;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
@Controller
public class Keyboards {
    @Autowired
    Bot bot;

    public Keyboards(Bot bot) {
        this.bot = bot;
    }

    InlineKeyboardMarkup inlineKeyboardMarkup;
    List<List<InlineKeyboardButton>> rowList;
    List<InlineKeyboardButton> keyboardButtonsRow;

    private InlineKeyboardMarkup getKeyboard(String kName) {
        switch (kName) {
            case "APP":
                inlineKeyboardMarkup = new InlineKeyboardMarkup();
                rowList = new ArrayList<>();
                keyboardButtonsRow = new ArrayList<>();
                if (bot.appProperty==null){
                    return null;
                }else {
                    for (String name : bot.appProperty.getNAME()
                    ) {
                        keyboardButtonsRow = new ArrayList<>();
                        keyboardButtonsRow.add(new InlineKeyboardButton().setText(name).setCallbackData("prop.app:" + name));
                        rowList.add(keyboardButtonsRow);
                    }
                    inlineKeyboardMarkup.setKeyboard(rowList);
                    return inlineKeyboardMarkup;
                }
            case "MODES":
                inlineKeyboardMarkup = new InlineKeyboardMarkup();
                rowList = new ArrayList<>();
                for (int i = 0; i < bot.appProperty.mode.size(); i++) {
                    keyboardButtonsRow = new ArrayList<>();
                    keyboardButtonsRow.add(new InlineKeyboardButton().setText(bot.appProperty.mode.get(i)).setCallbackData("prop.mode:" + (i + 1)));
                    rowList.add(keyboardButtonsRow);
                }
                inlineKeyboardMarkup.setKeyboard(rowList);
                return inlineKeyboardMarkup;
            default:
                return null;
        }
    }
    enum Keyboard{
        APP{
            @Override
            public InlineKeyboardMarkup getKeyboard(Bot bot) {
                return new Keyboards(bot).getKeyboard("APP");
            }
        },MODES{
            @Override
            public InlineKeyboardMarkup getKeyboard(Bot bot) {
                return new Keyboards(bot).getKeyboard("MODES");
            }
        };
        public abstract InlineKeyboardMarkup getKeyboard(Bot bot);
    }
}
