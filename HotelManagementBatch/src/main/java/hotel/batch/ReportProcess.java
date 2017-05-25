package hotel.batch;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportProcess {

// 	@Autowired
//	ProductService productService;
	
 	// Called from ProductBatch - when job is completed
 	// Prints out a summary of processed Products
 	public void start() throws IOException {
		
 		System.out.println("******* Reports Finished===>");
 		
 		
		System.out.println();
		 
	}
}
