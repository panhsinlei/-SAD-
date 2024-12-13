import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class ArticleHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (path.startsWith("/article/")) {
            String articleIdStr = path.substring(9); // 提取文章 ID
            int id = Integer.parseInt(articleIdStr);

            Article article = Article.getArticleById(id);

            if (article != null) {
                String response = "<html><body><h1>" + article.getTitle() + "</h1>"
                        + "<p>" + article.getContent() + "</p>"
                        + "<p><strong>Tag: </strong>" + article.getTag() + "</p>"
                        + "<a href=\"/articles\">Back to article list</a></body></html>";

                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } else {
                exchange.sendResponseHeaders(404, -1); // 文章未找到
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // 方法不允許
        }
    }
}
