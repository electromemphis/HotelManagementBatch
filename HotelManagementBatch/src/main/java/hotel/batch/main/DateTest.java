package hotel.batch.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import hotel.batch.ReportWriter;
import hotel.service.ReportService;
import hotel.service.impl.ReportServiceImpl;

public class DateTest {
	
	public static void main(String[] args) {
		
//		
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
//        String dateInString = "25-May-2017";
//
//        try {
//
//            Date date = formatter.parse(dateInString);
//            System.out.println(date);
//            System.out.println(formatter.format(date));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
        String inputString1 = "24 05 2017";
        String inputString2 = "27 05 2017";

        try {
            Date date1 = myFormat.parse(inputString1);
            Date date2 = myFormat.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();
            System.out.println("diff="+diff);
            System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        ReportWriter writer = new ReportWriter();
        ReportService service = new ReportServiceImpl();
        writer.setRService(service);
        try {
			writer.execute(null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
