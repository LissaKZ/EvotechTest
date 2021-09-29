package Telegram;


import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.api.objects.File;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class User extends Thread {
    public String app;
    public Long chatId;
    LocalDateTime lastUpdate;
    private int limit;
    public boolean on;
    ArrayList<String[]> dialogs;
    private String botToken;
    public String[] sources;

    public String[] getSources() {
        return sources;
    }

    public void setSources(String sources) {
        String[] channels=sources.split(" ");
        this.sources = channels;
    }

    public String[] getDialog() {
        return dialogs.get(new Random().nextInt(dialogs.size()));
    }

    public void setDialog(String message) {
        dialogs=new ArrayList<>();
        for (String d: message.split("\\r?\\n")
             ) {
            dialogs.add(d.split(","));
        }
        this.dialogs=dialogs;
    }

    public String getApp() {
        return app;
    }

    public boolean isOn() {
        return on;
    }

    public void turnOff() {
        this.on = false;
    }

    String mode;
    HashMap<ArrayList<String>,String> testCase;


    public String getMode() {
        return mode;
    }

    ArrayList<String> path;
    ArrayList<String> keywords;
    ArrayList<String> target;

    public FileParser getFile() {
        return file;
    }


    FileParser file=new FileParser();

    public ArrayList<String> getTarget() {
        return target;
    }

    public void setTarget(String message) {
        target=new ArrayList<>();;
        for (String s: message.split("\\r?\\n")
        ) {
            target.add(s);
        }
    }

    public void setPath(String message) {
        path=new ArrayList<>();
        if(!message.equals("null")) {
            for (String s : message.split("\\r?\\n")
            ) {
                path.add(s);
            }
        }
    }

    public HashMap<ArrayList<String>,String> getTestCase() {
        return testCase;
    }

    public void setTestCase(File file) {
        testCase = new HashMap<>();
        path = new ArrayList<>();
        java.io.File localFile = new java.io.File("test" + chatId + ".xlsx");
        InputStream is = null;
        Sheet sheet;
        try {
            is = new URL("https://api.telegram.org/file/bot" + botToken + "/" + file.getFilePath()).openStream();
            FileUtils.copyInputStreamToFile(is, localFile);
            Workbook wb = WorkbookFactory.create(localFile);
            sheet = wb.getSheetAt(0);
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                try {
                    path.add(String.valueOf(i+1));
                    for (int j = 1; j < sheet.getRow(i).getLastCellNum(); j++) {
                        path.add(sheet.getRow(i).getCell(j).getStringCellValue());
                    }
                    testCase.put(path, sheet.getRow(i).getCell(0).getStringCellValue());
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                path = new ArrayList<>();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(String message) {
        keywords=new ArrayList<>();;
        for (String s: message.split("\\r?\\n")
        ) {
            keywords.add(s);
        }
    }
    public void setKeywords(File file) throws FileNotFoundException {
        keywords=new ArrayList<>();
        try {
            java.io.File localFile = new java.io.File("test"+chatId+".txt");
        InputStream is = new URL("https://api.telegram.org/file/bot"+botToken+"/"+file.getFilePath()).openStream();
        FileUtils.copyInputStreamToFile(is, localFile);
            BufferedReader reader=new BufferedReader(new FileReader(localFile));
            String s=reader.readLine();
            while (s!=null){
                keywords.add(s);
                s=reader.readLine();
            }

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    public User(Long chatId) {
        this.setParam("chatId",chatId);
        this.botToken=Bot.botToken;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }



    public void setParam(String param, String value) {
        switch (param.split("\\.")[1]){
            case "app":
                this.app=value;

            case "mode":
                this.mode=value;
        }

    }
    public void setParam(String param,Long value) {
        switch (param){
            case "chatId":
                this.chatId=value;
                break;
        }
    }

    public void clear(){
        path=null;
        keywords=null;
        target=null;
        sources=null;
    }

    public ArrayList<String> getPath() {
        return path;
    }

    public void setLimit(int parseInt) {
        this.limit=parseInt;
    }

    public int getLimit() {
        return limit;
    }

    public void turnOn() {
        this.on=true;
    }
}
