package hotel.batch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hotel.batch.ReportProcess;

@Component
public class ReportBatch {

	@Autowired
	Job dailyReport;
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	ReportProcess reportProcess;
	
//	@Scheduled(fixedDelay= 15000, initialDelay=2000)
//	@Scheduled(cron = "0 0 8-10 * * *")
	@Scheduled(cron = "*/5 * * * * *")
	public void startjob() throws  Exception {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println("Starting batch " + dateFormat.format(date));  
		
 	    JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
	    jobParametersBuilder.addDate("date", new Date());
	    JobParameters jobParameters = jobParametersBuilder.toJobParameters();
	    JobExecution jobExecution = jobLauncher.run(dailyReport, jobParameters);
	    BatchStatus batchStatus = jobExecution.getStatus();
	    
	    while (batchStatus.isRunning()) {
	        System.out.println("Still running...");
	        Thread.sleep(1000);
	    }
	    System.out.println("Exit status: " + jobExecution.getExitStatus().getExitCode());

	    JobInstance jobInstance = jobExecution.getJobInstance();
	    System.out.println("job instance Id: " + jobInstance.getId());
	    
	    // Print out current results...
	    // Further Product processing will be done interactively through ValidationGroupsDesktop
		reportProcess.start();
	    System.out.println("DONE PROCESS");

//	    System.exit(0);
	}
	
}
