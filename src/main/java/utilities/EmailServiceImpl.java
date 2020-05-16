package utilities;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import objects.Article;

@Component
public class EmailServiceImpl implements EmailService {
  
    @Autowired
    public JavaMailSender emailSender;
    
    public void sendSimpleMessage(
    		//maybe change this arraylist of strings to "article" objects
      String to, List <Article> articlesList) {
        
    	String body="";
    	for(Article a:articlesList) {
    		body += a.getTitle() + "\n";
    		body += a.getContent() + "\n";
    		body += a.getUrl();
    		body += "\n\n";
    	}
    	//TODO change and include todays date
    	String subject = "testSubject";
    	try {
    		System.out.println("Email body:\n"+body);
    		//TODO: In future, look at making a template to use
    		SimpleMailMessage message = new SimpleMailMessage(); 
            message.setTo(to); 
            message.setSubject(subject); 
            message.setText(body);
            emailSender.send(message);
            System.out.println("--------------EMAIL SENT---------------");
    	} catch (Exception e) {
    		System.out.println("--------------ERROR SENDING EMAIL---------------");
    		e.printStackTrace();
    	}
    }

}
