package webCrawler;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg {
  private static final String USER_AGENT =
      "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
  private List<String> links = new LinkedList<>();
  private Document document;

  public boolean crawl(String url) {
    try {
      Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
      Document document = connection.get();
      this.document = document;
      if (connection.response().statusCode() == 200) {
        System.out.println("\nReceived web page at " + url);
      }
      if (!connection.response().contentType().contains("text/html")) {
        System.out.println("Retrieved something other than HTML");
        return false;
      }
      Elements linksOnPage = document.select("a[href]");
      System.out.println("Found (" + linksOnPage.size() + ") links");
      for (Element link : linksOnPage) {
        this.links.add(link.absUrl("href"));
      }
      return true;
    } catch (IOException ioe) {
      return false;
    }
  }

  public boolean searchForWord(String keyword) {
    if (this.document == null) {
      System.out.println("ERROR! Call crawl() before performing analysis on the document");
      return false;
    }
    System.out.println("Searching for the word " + keyword + "...");
    String bodyText = this.document.body().text();
    return bodyText.toLowerCase().contains(keyword.toLowerCase());
  }

  public List<String> getLinks() {
    return this.links;
  }
}
