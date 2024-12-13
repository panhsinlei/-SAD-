import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ArticleListHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        List<Article> articles;
        String tag = null;

        if (query != null && query.startsWith("tag=")) {
            tag = query.substring(4); // 提取標籤
        }

        articles = (tag != null) ? Article.getArticlesByTag(tag) : new ArrayList<>(Article.getAllArticles().values());

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            json.append("{\"id\":").append(article.getId())
                .append(",\"title\":\"").append(article.getTitle())
                .append("\",\"tag\":\"").append(article.getTag()).append("\"}");
            if (i < articles.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");

        String response = new String(Files.readAllBytes(Paths.get("C:\\Users\\a\\Desktop\\SAD project\\articles.html")), "UTF-8");
        response = response.replace("</ul>", 
            "</ul><script>const articles = " + json + ";const ul = document.getElementById('article-list');" +
            "articles.forEach(a => { const li = document.createElement('li'); li.innerHTML = `<a href='/article/${a.id}'>${a.title}</a> - [${a.tag}]`; ul.appendChild(li); });</script>");
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.getBytes("UTF-8").length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes("UTF-8"));
        }
    }
}
