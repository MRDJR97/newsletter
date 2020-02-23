package application;

public class Article {

	  private final long id;
	  private final String content;

	  public Article(long id, String content) {
	    this.id = id;
	    this.content = content;
	  }

	  public long getId() {
	    return id;
	  }

	  public String getContent() {
	    return content;
	  }
}
