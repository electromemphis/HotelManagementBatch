/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package hotel.service;

import java.io.File;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import hotel.domain.report.Report;

@Service("emailService")
public class EmailService {

     private static final String LOGO = "templates/images/logo.jpg";
    
     private static final String JPG_MIME = "image/jpg";

  @Autowired
    private JavaMailSender mailSender;
 
  @Autowired
    private SpringTemplateEngine templateEngine;
    
    /* 
     * Send HTML mail  
     */
    public void sendOrderReceivedMail(
            final String recipientName, final String recipientEmail, Report report,String documentName,final Locale locale) 
            throws MessagingException,Exception {
        
        // Prepare the Thymeleaf evaluation context
        final Context thymeContext = new Context(locale);
        thymeContext.setVariable("name", recipientName);
        thymeContext.setVariable("report", report);
          
        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true,"UTF-8");
        message.setSubject("Hotel Management Daily Report");
 
        // could have CC, BCC, will also take an array of Strings
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf..template is hotelManagementMail.html
        final String htmlContent = this.templateEngine.process("hotelManagementMail", thymeContext);
        message.setText(htmlContent, true /* isHtml */);
   
        // Add the logo
        message.addInline("logo", new ClassPathResource(LOGO), JPG_MIME);
        
        
        // Add attachment
        message.addAttachment(documentName, new File("src/main/resources/reports/"+documentName));
        // Send email
        this.mailSender.send(mimeMessage);

    }

 
}
