import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class ArticleListHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            Map<Integer, String[]> articles = Article.getAllArticles();

            StringBuilder response = new StringBuilder("<html><body><h1>Articles</h1><ul>");
            for (Map.Entry<Integer, String[]> entry : articles.entrySet()) {
                response.append("<li><a href=\"/article/").append(entry.getKey())
                        .append("\">").append(entry.getValue()[0]).append("</a></li>");
            }
            response.append("</ul><a href=\"/\">Back to home</a></body></html>");

            exchange.sendResponseHeaders(200, response.toString().getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.toString().getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // 方法不允許
        }
    }
}
