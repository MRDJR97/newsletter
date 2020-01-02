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

	public void executeSearch(){
		
		BingNewsSearchAPI client = BingNewsSearchManager.authenticate(subscriptionKey);
	    
		try {

            //=============================================================
            // This will search news for (Quantum  Computing) with market and count parameters then verify number of
            // results and print out total estimated matches, name, url, description, published time and name of provider
            // of the first item in the list of news result list.

            System.out.println("Search news for query \"Quantum  Computing\" with market and count");
            NewsModel newsResults = client.bingNews().search()
                .withQuery("Quantum  Computing")
                .withMarket("en-us")
                .withCount(100)
                .execute();

            PrintNewsResult(newsResults);


            //=============================================================
            // This will search most recent news for (Artificial Intelligence) with freshness and sort-by parameters then
            //  verify number of results and print out totalEstimatedMatches, name, url, description, published time and
            //  name of provider of the first news result

            System.out.println("Search most recent news for query \"Artificial Intelligence\" with freshness and sortBy");
            newsResults = client.bingNews().search()
                .withQuery("Artificial Intelligence")
                .withMarket("en-us")
                .withFreshness(Freshness.WEEK)
                .withSortBy("Date")
                .execute();

            PrintNewsResult(newsResults);


            //=============================================================
            // This will search category news for movie and TV entertainment with safe search then verify number of results
            //  and print out category, name, url, description, published time and name of provider of the first news result

            System.out.println("Search category news for movie and TV entertainment with safe search");
            newsResults = client.bingNews().category()
                .withMarket("en-us")
                .withCategory("Entertainment_MovieAndTV")
                .withSafeSearch(SafeSearch.STRICT)
                .execute();

            PrintNewsResult(newsResults);


            //=============================================================
            // This will search news trending topics in Bing then verify number of results and print out name, text of query,
            //  webSearchUrl, newsSearchUrl and image Url of the first news result

            System.out.println("Search news trending topics in Bing");
            TrendingTopics trendingTopics = client.bingNews().trending()
                .withMarket("en-us")
                .execute();

            if (trendingTopics != null) {
                if (trendingTopics.value().size() > 0) {
                    NewsTopic firstTopic = trendingTopics.value().get(0);

                    System.out.println(String.format("Trending topics count: %s", trendingTopics.value().size()));
                    System.out.println(String.format("First topic name: %s", firstTopic.name()));
                    System.out.println(String.format("First topic query: %s", firstTopic.query().text()));
                    System.out.println(String.format("First topic image url: %s", firstTopic.image().url()));
                    System.out.println(String.format("First topic webSearchUrl: %s", firstTopic.webSearchUrl()));
                    System.out.println(String.format("First topic newsSearchUrl: %s", firstTopic.newsSearchUrl()));
                } else {
                    System.out.println("Couldn't find news trending topics!");
                }
            } else {
                System.out.println("Didn't see any news trending topics..");
            }
        } catch (Exception f) {
            System.out.println(f.getMessage());
            f.printStackTrace();
        }
	}
	
	public static void PrintNewsResult(NewsModel newsResults) {
        if (newsResults != null) {
            if (newsResults.value().size() > 0) {
                NewsArticle firstNewsResult = newsResults.value().get(0);

                System.out.println(String.format("TotalEstimatedMatches value: %d", newsResults.totalEstimatedMatches()));
                System.out.println(String.format("News result count: %d", newsResults.value().size()));
                System.out.println(String.format("First news name: %s", firstNewsResult.name()));
                System.out.println(String.format("First news url: %s", firstNewsResult.url()));
                System.out.println(String.format("First news description: %s", firstNewsResult.description()));
                System.out.println(String.format("First news published time: %s", firstNewsResult.datePublished()));
                System.out.println(String.format("First news provider: %s", firstNewsResult.provider().get(0).name()));
            } else {
                System.out.println("Couldn't find news results!");
            }
        } else {
            System.out.println("Didn't see any news result data..");
        }
    }
}
