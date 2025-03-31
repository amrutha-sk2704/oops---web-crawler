package webCrawler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getParameter("url");
        String keyword = request.getParameter("keyword");

        Spider mySpider = new Spider();
        mySpider.search(url, keyword);
        List<String> pagesWithKeyword = mySpider.getPagesWithKeyword();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Search Results :</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: 100vh; }");
        out.println("h2 { color: #333; margin-bottom: 10px; }");
        out.println("ul { list-style-type: none; padding: 50px; }");
        out.println("li { background-color: #fff; padding: 10px; margin-bottom: 10px; border-radius: 4px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
        out.println("a { color: #0066cc; text-decoration: none; }");
        out.println("a:hover { text-decoration: underline; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Search Results</h2>");

        if (pagesWithKeyword.isEmpty()) {
            out.println("<p>No pages found with the specified keyword.</p>");
        } else {
            out.println("<ul>");
            for (String page : pagesWithKeyword) {
                out.println("<li><a href=\"" + page + "\">" + page + "</a></li>");
            }
            out.println("</ul>");
        }

        out.println("</body>");
        out.println("</html>");
    }
}