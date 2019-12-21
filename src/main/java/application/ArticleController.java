package application;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  //@RequestMapping(method=GET)
  @RequestMapping("/article")
  public Article greeting(@RequestParam(value="name", defaultValue="World") String name) {
    return new Article(counter.incrementAndGet(),
              String.format(template, name));
  }
}
