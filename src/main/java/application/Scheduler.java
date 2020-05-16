package application;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchAPI;
import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchManager;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.Freshness;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.NewsArticle;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.NewsModel;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.NewsTopic;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.SafeSearch;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.TrendingTopics;

import objects.Article;
import utilities.EmailService;
import utilities.EmailServiceImpl;

@Component
public class Scheduler {

	
	@Value("#{'${queries}'.split(',')}") 
	private List<String> queriesList;
	private static final Logger log = LoggerFactory.getLogger(Scheduler.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private static final String smmryURL = "http://api.smmry.com/SM_API_KEY=03BB18EB05";
	
	//every 120 seconds
	@Scheduled(fixedRate = 120000)
	public void formulateNewsContent() {
		System.out.println("-----------Running Scheduler----------");
		//Bing subscription key
		String subscriptionKey = "541c79c3eb314aa895d2f288b8cf9730";//how to store this? ensure it cant be seen in the code on github
		String adminEmail = "danieljohnregan@gmail.com";
		BingClient client = new BingClient(subscriptionKey);
		
		//todo: create email template here, then each iteration
		//and populate template with articles to send to Daniel
		//addcurrentdate to articleID (backwards for sorting)
		int articleId = 100;
		List <Article> allArticlesForEmail = new ArrayList<Article>();
		for(String query:queriesList){
			
			List<NewsArticle> allReturnedArticlesList = new ArrayList<NewsArticle>();
			System.out.println("RUNNING QUERY FOR: " + query);
			try {
				
				Thread.sleep(3000);
				//test what happens if this fails
				allReturnedArticlesList = client.executeSearch(query);
			} catch (Exception e) {
				//handle error
				e.printStackTrace();
			}
			for (NewsArticle article: allReturnedArticlesList) {
				//do check for description length that not too long
				Article a = new Article(articleId, article.name(), article.url(), article.description());
				allArticlesForEmail.add(a);
				articleId++;
			}
			//TODO: Add to email template with its position in arraylist
			EmailServiceImpl emailService = new EmailServiceImpl();
			emailService.sendSimpleMessage(adminEmail, allArticlesForEmail);
		}
		//daniel responds to email with a list of numbers, which are included in the email
		//or automatically create google form and email to daniel, if theres api
		
		
	    //---------------------------------------------------------------------------
		log.info("******The time is now {}", dateFormat.format(new Date()));
		/*for(int i=0; i<=numArticles; i++){
			
		}
		String articleURL = "";
		String targetURL = smmryURL + "&SM_URL=" + articleURL;
		String response = sendPost(smmryURL, articleURL);*/
		// check response code from URL and notify if error
	}

	private String sendPost(String targetURL, String urlParameters) {

		HttpURLConnection connection = null;
		try {
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length",
					Integer.toString(urlParameters.getBytes().length));

			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if
															// Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			// Need to do something here: error handling
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
}