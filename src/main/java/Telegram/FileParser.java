package Telegram;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class FileParser {
   private static HSSFWorkbook workbook;
    private static HSSFSheet sheet;

    private static int rownum;
    private static Cell cell;
    private static Row row;
    private static final String[] labels_mode1={"КС","Диалог Id","Промпт","Статус"};
    private static final String[] labels_mode2={"Статус","Желаемый action","Полученый action","Диалог Id"};

    private static HSSFCellStyle style;
    private static FileOutputStream outFile;
    private static File file;
    String mode;

    public FileParser() {
    }

    public void createNewFile(Long chatId) throws FileNotFoundException {
        file = new File("C:/demo/results"+chatId+".xls");
        file.getParentFile().mkdirs();
        outFile = new FileOutputStream(file);
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("result");
        style = createStyleForTitle(workbook);
        rownum=0;
        this.mode=UserManager.getUser(chatId).getMode();
        createColumns();

    }

    private static void addCell(int pos, String value){
        cell = row.createCell(pos, CellType.STRING);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
    private  void createColumns(){
        row = sheet.createRow(rownum);
        switch (mode){
            case "1":
                for (int i = 0; i < labels_mode1.length; i++) {
                    addCell(i, labels_mode1[i]);
                }
                break;
            case "2":
                for (int i = 0; i < labels_mode2.length; i++) {
                    addCell(i, labels_mode2[i]);
                }
                break;
        }
        rownum++;
    }
    public static void fillRow(String[] data){
        System.out.println(data[0]);
        row = sheet.createRow(rownum);
        rownum++;
        int i=0;
        for (String value : data) {
           cell = row.createCell(i, CellType.STRING);
           cell.setCellValue(value);
           i++;
        }
    }

    public static void stopWriting(){
        try {
            workbook.write(outFile);
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File getFile(){
        return file;
    }

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }
}
