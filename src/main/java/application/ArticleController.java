package application;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import objects.Article;

@RestController
public class ArticleController {

  private static final String template = "Hello, %s!";
  private int counter = 0;
  //@RequestMapping(method=GET)
  @RequestMapping("/article")
  public Article greeting(@RequestParam(value="name", defaultValue="World") String name) {
    return new Article(counter++, String.format(template, name), String.format(template, name), String.format(template, name));
  }
}
