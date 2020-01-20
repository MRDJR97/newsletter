package application;

import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchAPI;
import com.microsoft.azure.cognitiveservices.search.newssearch.BingNewsSearchManager;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.Freshness;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.NewsArticle;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.NewsModel;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.NewsTopic;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.SafeSearch;
import com.microsoft.azure.cognitiveservices.search.newssearch.models.TrendingTopics;

//sample code & examples: https://github.com/Azure-Samples/cognitive-services-java-sdk-samples/blob/master/Search/BingNewsSearch/src/main/java/com/microsoft/azure/cognitiveservices/search/newssearch/samples/BingNewsSearchSample.java
//maximum URL length is 2,048 characters
public class BingClient {

	String subscriptionKey;
	
	public BingClient(String subscriptionKey) {
		this.subscriptionKey = subscriptionKey;
	}

	public void executeSearch(String query){
		
		BingNewsSearchAPI client = BingNewsSearchManager.authenticate(subscriptionKey);
	    
		try {

			NewsModel newsResults = client.bingNews().search()
                .withQuery(query)
                .withMarket("en-us")
                .withFreshness(Freshness.DAY)
                .withSafeSearch(SafeSearch.MODERATE)
                .withCount(10)
                .execute();

            PrintNewsResult(newsResults, query, 3);

        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        }
	}
	
	public static void PrintNewsResult(NewsModel newsResults, String query, int numArticles) {
        if (newsResults != null) {
            if (newsResults.value().size() >= numArticles) {
            	System.out.println("\n\n"+query);
            	for(int i=0; i<numArticles; i++){
            		
            		NewsArticle result = newsResults.value().get(i);
                    System.out.println(String.format("First news name: %s", result.name()));
                    System.out.println(String.format("First news url: %s", result.url()));
                    System.out.println(String.format("First news description: %s", result.description())+"\n");
            	}
                
            } else {
                System.out.println("Error: Couldn't find news results!");
            }
        } else {
            System.out.println("Error: Didn't see any news result data..");
        }
    }
}
