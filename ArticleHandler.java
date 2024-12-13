
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
                String response = "<html><head><title>" + article.getTitle() + "</title>"
                        + "<link rel=\"stylesheet\" href=\"/styles.css\"></head><body>"
                        + "<header><h1>資管學習資源平台</h1></header>"
                        + "<main><h2>" + article.getTitle() + "</h2>"
                        + "<p>" + article.getContent() + "</p>"
                        + "<p><strong>Tag: </strong>" + article.getTag() + "</p>"
                        + "<a href=\"/articles\">返回文章列表</a></main></body></html>";


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