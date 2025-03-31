package webCrawler;

import java.util.List;

public class SpiderTest {
  public static void main(String[] args) {
    Spider mySpider = new Spider();
    mySpider.search("http://geeksforgeeks.org", "Stacks");
    List<String> pagesWithKeyword = mySpider.getPagesWithKeyword();
    for (String page : pagesWithKeyword) {
      System.out.println(page);
    }
  }
}
