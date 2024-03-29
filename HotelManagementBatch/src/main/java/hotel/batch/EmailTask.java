package hotel.batch;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import hotel.domain.report.Report;
import hotel.service.EmailService;

public class EmailTask implements Tasklet{
	
	@Autowired
	private EmailService emailService;

	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		
		System.out.println(" Sending EMAIL ....");

	    Report report = new Report();
	    report.setId(12);
	    report.setDate(new Date(System.currentTimeMillis()));
	    
	    String documentName = "HotelDailyReport.xlsx";
	    try{
	    	emailService.sendOrderReceivedMail("Kimchi", "electromemphis1@gmail.com",report,documentName,new Locale("en"));
	    	
	    }catch (Exception e) {
			e.printStackTrace();
		}
		
		return RepeatStatus.FINISHED;
	}

}
