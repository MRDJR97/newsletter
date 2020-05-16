package objects;

public class Article {

	  private final int id;
	  private final String title;
	  private String url;
	  private String content;
	  

	  public Article(int id, String title, String url, String content) {
	    
		this.id = id;
		this.title = title;
		this.url = url;
	    this.content = content;
	  }

	  public long getId() {
	    return id;
	  }

	  public String getContent() {
	    return content;
	  }

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}
	  
}
