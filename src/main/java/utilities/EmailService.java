package utilities;

import java.util.List;

import objects.Article;

public interface EmailService {
	
    void sendSimpleMessage(String to, List <Article> articlesList);
    
}