package hotel.batch.main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsWriterTest {
	

    private static final String FILE_NAME = "src/main/resources/reports/MyFirstExcel.xlsx";
    
    private static int populateRows(Object[][] reports, XSSFSheet sheet, int startRow){
    	
        for (Object[] report : reports) {
            Row row = sheet.createRow(startRow++);
            int colNum = 0;
            for (Object field : report) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                } else if (field instanceof Double) {
                    cell.setCellValue((Double) field);
                }
            }
        }
        
        return startRow;
    	
    }
    
    public static void main(String[] args) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Daily Report");
        
        int rowNum = 0;
        //header
        Row row = sheet.createRow(rowNum++);
        Cell cell = row.createCell(0,3);
        cell.setCellValue("Hotel Management Daily Report");
        sheet.createRow(rowNum++);
        
        Object[][] roomReport = {
                {"Room Status", "Count", "Total Cost"},
                {"Checked In", 23, 23.23},
                {"Checked Out", 34, 45.45}
        };
        
        System.out.println("Creating excel");
        
        rowNum = populateRows(roomReport, sheet, rowNum);
        sheet.createRow(rowNum++);
        
        Object[][] reservationReport = {
                {"Reservation Status", "Count", "Total Cost"},
                {"Checked In", 23, 23.23},
                {"Checked Out", 34, 45.45},
                {"Confirmed", 34, 45.45},
        };
        populateRows(reservationReport, sheet, rowNum);


        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
        	e.printStackTrace();
        }

        System.out.println("Done");
    }

}
