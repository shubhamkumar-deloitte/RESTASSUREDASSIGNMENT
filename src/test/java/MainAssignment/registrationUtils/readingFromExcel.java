package MainAssignment.registrationUtils;

import org.apache.commons.compress.utils.FixedLengthBlockOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class readingFromExcel {

    static Logger logger= LogManager.getLogger(readingFromExcel.class.getName());


    public static userClass registerUser() throws IOException {

        userClass userClass=null;

        String excelFilePath="C:\\Users\\shubhamkumar32\\IdeaProjects\\restAssured\\src\\test\\java\\MainAssignment\\UserDetails.xlsx";
        FileInputStream fis=new FileInputStream(excelFilePath);
        XSSFWorkbook workbook=new XSSFWorkbook(fis);
        XSSFSheet sheet=workbook.getSheetAt(0);
        XSSFRow row= sheet.getRow(1);

        userClass=new userClass(row.getCell(0).toString(),row.getCell(1).toString(),row.getCell(2).toString(),(int)row.getCell(3).getNumericCellValue());

        return userClass;
    }
    public static List<String> readAllTasksFromExcel() throws IOException {
        List<String> allTasks=new ArrayList<>();

        String excelFilePath="C:\\Users\\shubhamkumar32\\IdeaProjects\\restAssured\\src\\test\\java\\MainAssignment\\UserDetails.xlsx";
        FileInputStream fis=new FileInputStream(excelFilePath);
        XSSFWorkbook workbook=new XSSFWorkbook(fis);
        XSSFSheet sheet=workbook.getSheetAt(1);
        XSSFRow row=null;
        XSSFCell cell=null;
        for(int i=1;i<sheet.getLastRowNum();i++){

            row= sheet.getRow(i);
            //all the task are listed in the cell zero

            allTasks.add(row.getCell(0).getStringCellValue());
        }
        return allTasks;
    }
}
