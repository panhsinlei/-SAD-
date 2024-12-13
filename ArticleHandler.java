
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ArticleHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (path.startsWith("/article/")) {
            int id = Integer.parseInt(path.substring(9)); // 提取文章 ID

            Article article = Article.getArticleById(id);

            if (article != null) {
                String response = new String(Files.readAllBytes(Paths.get("C:\\Users\\a\\Desktop\\SAD project\\article.html")), "UTF-8");
                response = response.replace("<title id=\"article-title\"></title>", "<title>" + article.getTitle() + "</title>")
                    .replace("<h2 id=\"article-header\"></h2>", "<h2>" + article.getTitle() + "</h2>")
                    .replace("<p id=\"article-content\"></p>", "<p>" + article.getContent() + "</p>")
                    .replace("<span id=\"article-tag\"></span>", article.getTag());
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.getBytes("UTF-8").length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes("UTF-8"));
                }
            } else {
                exchange.sendResponseHeaders(404, -1); // 文章未找到
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // 方法不允許
        }
    }
}