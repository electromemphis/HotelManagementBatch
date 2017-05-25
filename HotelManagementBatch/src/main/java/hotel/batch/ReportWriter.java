package hotel.batch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import hotel.domain.report.Report;
import hotel.domain.report.RoomReport;
import hotel.service.EmailService;
import hotel.service.ReportService;

/*
 * Declared in user-job.xml
 */
public class ReportWriter implements Tasklet{
	
	private static final String FILE_NAME = "src/main/resources/reports/HotelDailyReport.xlsx";
	
	@Autowired
	ReportService reportService;
	
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {

		System.out.println("============ReportWriter=============");
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Daily Report");
		
		Report report = reportService.getReport(new Date());
	        
		int rowNum = 0;
		//header
		Row row = sheet.createRow(rowNum++);
		Cell cell = row.createCell(0,3);
		cell.setCellValue(report.getReportTitle());
		sheet.createRow(rowNum++);
		
		Row rowCk = sheet.createRow(rowNum++);
		Cell cellcKRooms = rowCk.createCell(0,3);
		cellcKRooms.setCellValue("Checked In Rooms:");
	    
		Object[][] ckRoomHeader = {
		        {"Room Number", "Price",  "Guest Name", "Checked In","Checked Out","Days", "Total Cost"}
		};
		rowNum = populateRows(ckRoomHeader, sheet, workbook, rowNum);
		
		for (RoomReport cReport : report.getCheckedInRooms()) {
			
			Object[][] cRm = {{
				cReport.getRoom().getRoomNumber(),
				cReport.getRoom().getPrice(),
				cReport.getGuestName(),
				cReport.getCheckinDate(),
				cReport.getCheckoutDate(),
				cReport.getTotalDays(),
				cReport.getTotalCost()}};
			
			rowNum = populateRows(cRm, sheet, workbook, rowNum);
		}
		
		sheet.createRow(rowNum++);
		Row rowCko = sheet.createRow(rowNum++);
		Cell cellcKORooms = rowCko.createCell(0,3);
		cellcKORooms.setCellValue("Checked Out Rooms:");

		rowNum = populateRows(ckRoomHeader, sheet, workbook, rowNum);
		
		for (RoomReport cReport : report.getCheckedOutRooms()) {
			
			Object[][] cRm = {{
				cReport.getRoom().getRoomNumber(),
				cReport.getRoom().getPrice(),
				cReport.getGuestName(),
				cReport.getCheckinDate(),
				cReport.getCheckoutDate(),
				cReport.getTotalDays(),
				cReport.getTotalCost()}};
			
			rowNum = populateRows(cRm, sheet, workbook, rowNum);
		}
		sheet.createRow(rowNum+=2);
		Row rowSummary = sheet.createRow(rowNum++);
		Cell cellSummary = rowSummary.createCell(0,3);
		cellSummary.setCellValue("Summary:");
		
		Object[][] cSummaryCheckedInTotal = {
		        {"checkin proceeds:", report.getTotalCheckinPrice()},
		        {"checkout proceeds:", report.getTotalCheckoutPrice()},
		};
		rowNum = populateRows(cSummaryCheckedInTotal, sheet, workbook, rowNum);
	        
		FileOutputStream outputStream = null;
		try {
			System.out.println("Creating excel");
		    outputStream = new FileOutputStream(FILE_NAME);
		    workbook.write(outputStream);
		
		    
		    
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}finally{
			
			try{
		        workbook.close();
		        System.out.println("report generated successfully.....");
		        outputStream.close();
				return RepeatStatus.FINISHED;	        			
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println("Done");

		
		return RepeatStatus.CONTINUABLE;
	}
	
	private int populateRows(Object[][] reports, XSSFSheet sheet, XSSFWorkbook wb, int startRow){
	    	
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
		        }else if (field instanceof Date) {
		            
		            CellStyle cellStyle = wb.createCellStyle();
		            CreationHelper createHelper = wb.getCreationHelper();
		            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));
		            cell.setCellValue((Date) field);
//		            cell.setCellValue(new Date());
		            cell.setCellStyle(cellStyle);
		        }
		    }
		}
		
		return startRow;
	    	
	}
	
	//for testing only. remove later.
	public void setRService(ReportService reportService){
		this.reportService = reportService;
	}


}

