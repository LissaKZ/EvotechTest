package Telegram;

import omilia.utils.OmiliaDialogHandler;

public enum Command {
    START{
        @Override
        public void executeCommand(Bot bot, Long chatId,String message) {
            Bot.omiliaDialogs.put(chatId, new OmiliaDialogHandler());
            UserManager.addUser(chatId);
           MessageSender.sendMessage(bot, chatId, "Выберите приложение", Keyboards.Keyboard.APP.getKeyboard(bot));
        }
        @Override
        public String getCommand() {
            return "/start";
        }
    },END{
        @Override
        public void executeCommand(Bot bot, Long chatId,String message) {

        }

        @Override
        public String getCommand() {
            return "/end";
        }
    }
    ,STOP{
        @Override
        public void executeCommand(Bot bot, Long chatId, String message) {
            UserManager.getUser(chatId).turnOff();
        }

        @Override
        public String getCommand() {
            return "/stop";
        }
    },
    CLEAN{
        @Override
        public void executeCommand(Bot bot, Long chatId, String message) {
            UserManager.getUser(chatId).clear();
        }

        @Override
        public String getCommand() {
            return "/clean";
        }
    },
    ADD_APP{
        @Override
        public void executeCommand(Bot bot, Long chatId, String message) {
            if (message.split("/add_app").length!=0&&message.split("/add_app ")[1].length()>=1){
                bot.appProperty.getNAME().add(message.replace("/add_app","").replace(" ",""));
                START.executeCommand(bot, chatId, message);
            }

        }

        @Override
        public String getCommand() {
            return "/add_app";
        }
    }

    ,TEST{
        @Override
        public void executeCommand(Bot bot, Long chatId,String message) {
            UserManager.getUser(chatId).turnOn();
           Test test=new Test(UserManager.getUser(chatId).mode,bot,chatId,UserManager.getUser(chatId).getPath(),UserManager.getUser(chatId).getKeywords(),UserManager.getUser(chatId).getTarget(),UserManager.getUser(chatId).getTestCase());
           test.start();
        }
        @Override
        public String getCommand() {
            return "/test";
        }
    };

    public abstract void executeCommand(Bot bot, Long chatId,String message);

    public abstract String getCommand();
}
