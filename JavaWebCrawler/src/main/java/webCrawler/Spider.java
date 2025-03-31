package webCrawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Spider {
  private static final int MAX_PAGES = 10;
  private Set<String> visitedPages = new HashSet<>();
  private List<String> pagesToExplore = new LinkedList<>();
  private List<String> visitedPagesList = new LinkedList<>();
  private List<String> pagesWithKeyword = new LinkedList<>();

  public void search(String url, String keyword) {
    while (this.visitedPages.size() < MAX_PAGES) {
      String currentUrl;
      SpiderLeg leg = new SpiderLeg();
      if (this.pagesToExplore.isEmpty()) {
        currentUrl = url;
        this.visitedPages.add(url);
        this.visitedPagesList.add(url);
      } else {
        currentUrl = this.getNextUrl();
      }
      leg.crawl(currentUrl);
      boolean found = leg.searchForWord(keyword);
      if (found) {
        System.out.println(String.format("\nWord %s found at %s", keyword, currentUrl));
        this.pagesWithKeyword.add(currentUrl);
      }
      this.pagesToExplore.addAll(leg.getLinks());
    }
    System.out.println("\n Visited " + this.visitedPages.size() + " web page(s)");
  }

  private String getNextUrl() {
    String nextUrl;
    do {
      nextUrl = this.pagesToExplore.remove(0);
    } while (this.visitedPages.contains(nextUrl));
    this.visitedPages.add(nextUrl);
    this.visitedPagesList.add(nextUrl);
    return nextUrl;
  }

  public List<String> getVisitedPages() {
    return visitedPagesList;
  }

  public List<String> getPagesWithKeyword() {
    return this.pagesWithKeyword;
  }
}
