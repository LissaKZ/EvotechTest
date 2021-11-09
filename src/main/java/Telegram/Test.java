package Telegram;

import omilia.action.OmEndDialog;
import omilia.action.OmStartDialog;
import omilia.response.OmResponse;
import omilia.response.parts.action.message.Message;
import omilia.response.parts.common.Field;
import omilia.utils.OmiliaDialogHandler;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.exceptions.TelegramApiException;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test extends Thread {
    private final HashMap<ArrayList<String>,String> testCase;
    ArrayList<String> path;
    ArrayList<String> keywords;
    String mode;
    ArrayList<String> targets;
    Bot bot;
    Long chatId;
    OmiliaDialogHandler handler;
    FileParser file;

    public Test(String mode, Bot bot, Long chatId, ArrayList<String> path, ArrayList<String> keywords, ArrayList<String> targets, HashMap< ArrayList<String>,String> testCase) {
        this.keywords=keywords;
        this.path=path;
        this.targets=targets;
        this.testCase=testCase;
        this.bot=bot;
        this.chatId=chatId;
        this.mode=mode;
    }
    public void run() {
            switch (mode){
                case "1":
                    createFile();
                    testInMode1();
                    sendFile();
                    break;
                case "2":
                    createFile();
                    testInMode2();
                    sendFile();
                    break;
                case "3":
                    testInMode3();
                    break;
            }
            UserManager.getUser(chatId).clear();
            stop();
    }

    private void testInMode3() {
        ThreadGroup threadGroup=new ThreadGroup("threads");

        while (UserManager.getUser(chatId).isOn()) {
            if (threadGroup.activeCount() < UserManager.getUser(chatId).getLimit()) {
                new Dialog("t" + threadGroup.activeCount(), threadGroup, bot, chatId, UserManager.getUser(chatId).getDialog());
            }
        }
    }

    private void testInMode2(){
        testCase.forEach((key, tab) ->{
            handler= new OmiliaDialogHandler();
            List<OmResponse> response = null;
            String res="";
            String dialogId ="";
            if(UserManager.getUser(chatId).isOn()) {
                dialogId = handler.execute(bot, new OmStartDialog(UserManager.getUser(chatId).app)).getDialogId();
                for (int i = 1; i < key.size(); i++) {
                    response = bot.sendToOmilia(handler, chatId, key.get(i));
                }

                handler.execute(bot, new OmEndDialog(UserManager.getUser(chatId).getApp()));
            }
            if(response!=null&&response.size()!=0){
                for (OmResponse resp: response
                     ) {
                    try {
                        res+=resp.getAction().getName()+",";
                        if (resp.getReaction().getName()!=null){
                            res+=resp.getReaction().getName()+",";
                        }
                    }catch (Exception e){
                        MessageSender.sendMessage(bot,chatId,"Что-то не так с response");
                        MessageSender.sendMessage(bot,chatId,e.toString());
                        MessageSender.sendMessage(bot,chatId,resp.toString());
                    }

                }
                res=res.substring(0,res.length()-1);

                file.fillRow(new String[]{key.get(0),String.valueOf(tab.replaceAll(" ","").replaceAll(",","").equalsIgnoreCase(res.replaceAll(" ","").replaceAll(",",""))),tab,res,dialogId});

            }else {
                file.fillRow(new String[]{"false",tab,null,dialogId});
            }
        });


    }

    private void testInMode1() {
        for (String keyword :keywords
        ) {
            if(UserManager.getUser(chatId).isOn()){
            List<OmResponse> responses;
            String prompt = "";
            String dialogId = null;
            for (String source:UserManager.getUser(chatId).getSources()
                 ) {
                if (UserManager.getUser(chatId).on) {
                    handler = new OmiliaDialogHandler();

                    try {
                        dialogId = handler.execute(bot, new OmStartDialog(UserManager.getUser(chatId).getApp(), source)).getDialogId();
                        for (String s : path) {
                            bot.sendToOmilia(handler, chatId, s);
                        }
                        responses = bot.sendToOmilia(handler, chatId, keyword);
                        for (OmResponse response : responses
                        ) {
                            if (response != null) {
                                prompt += response.getPrompt() + " ";
                            } else {
                                System.out.println("response is null");
                            }
                        }
                        if (responses != null) {
                            file.fillRow(new String[]{keyword, source, dialogId, prompt, String.valueOf(targets.contains(prompt))});
                        } else {
                            file.fillRow(new String[]{keyword, source, dialogId, null, "false"});
                        }
                        handler.execute(bot, new OmEndDialog(UserManager.getUser(chatId).getApp()));
                    } catch (Exception e) {
                        MessageSender.sendMessage(bot, chatId, "Не получается получить ответ от сервера.");
                        MessageSender.sendMessage(bot, chatId, e.toString());
                        MessageSender.sendMessage(bot, chatId, "Проверьте доступнсть сервера, впн и название приклада");
                        Command.CLEAN.executeCommand(bot, chatId, "");
                        file.fillRow(new String[]{keyword, "", "Ошибка", ""});
                        break;
                    }
                }else {break;}
            }

            }else {
                break;
            }
        }
    }
    public void createFile(){
        file=UserManager.getUser(chatId).getFile();
        try {
            file.createNewFile(chatId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void sendFile(){
        file.stopWriting();
        SendDocument sendDocument=new SendDocument();
        sendDocument.setNewDocument(file.getFile());
        sendDocument.setChatId(chatId);
        try {
            bot.sendDocument(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}