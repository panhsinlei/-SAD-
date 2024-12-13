import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ArticleListHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery(); // e.g., "tag=最新科技趨勢"
        String tag = null;
        if (query != null && query.startsWith("tag=")) {
            tag = query.substring(4); // 提取標籤值
        }

        List<Article> articles = (tag != null) ? Article.getArticlesByTag(tag) : new ArrayList<>(Article.getAllArticles().values());

        StringBuilder response = new StringBuilder("<html><body><h1>Articles</h1><ul>");
        for (Article article : articles) {
            response.append("<li><a href=\"/article/").append(article.getId())
                    .append("\">").append(article.getTitle()).append("</a> - [").append(article.getTag()).append("]</li>");
        }
        response.append("</ul>");

        // 加入標籤篩選鏈接
        response.append("<h2>Filter by Tag</h2>")
                .append("<a href=\"/articles?tag=最新科技趨勢\">最新科技趨勢</a><br>")
                .append("<a href=\"/articles?tag=職涯經驗分享\">職涯經驗分享</a><br>")
                .append("<a href=\"/articles?tag=課外閱讀推薦\">課外閱讀推薦</a><br>");

        response.append("<a href=\"/\">Back to home</a></body></html>");

        exchange.sendResponseHeaders(200, response.toString().getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.toString().getBytes());
        }
    }
}