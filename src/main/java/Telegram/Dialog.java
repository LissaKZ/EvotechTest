package Telegram;

import omilia.action.OmEndDialog;
import omilia.action.OmStartDialog;
import omilia.response.OmResponse;
import omilia.response.parts.common.Field;
import omilia.utils.OmiliaDialogHandler;

import java.util.List;

public class Dialog extends Thread {

    Bot bot;
    Long chatId;
    OmiliaDialogHandler handler;
    String[] keywords;
    String dialogId;
    Dialog(String threadname, ThreadGroup tg,Bot bot,Long chatId,String[] keywords)
    {
        super(tg, threadname);
        start();
        this.bot=bot;
        this.chatId=chatId;
        this.keywords=keywords;
    }
    public void run()
    {
        handler=new OmiliaDialogHandler();
        try {
            OmResponse responce=handler.execute(bot,new OmStartDialog(UserManager.getUser(chatId).getApp()));
            if(responce!=null) {
                dialogId = handler.execute(bot, new OmStartDialog(UserManager.getUser(chatId).getApp())).getDialogId();
                try {
                    for (String s : keywords) {
                        bot.sendToOmilia(handler, chatId, s);
                    }
                } catch (NullPointerException e) {

                }

                handler.execute(bot, new OmEndDialog(UserManager.getUser(chatId).getApp()));
            }else {
                MessageSender.sendMessage(bot,chatId,"Не удается получить ответ от сервера.");
            }
        }catch (Exception e){
            MessageSender.sendMessage(bot,chatId,"Не удается получить ответ от сервера.");
            MessageSender.sendMessage(bot,chatId,e.toString());
        }

    }
}
