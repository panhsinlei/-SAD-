import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ArticleListHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery(); // e.g., "keyword=example"
        List<Article> articles;
        String keyword = null;
        String tag = null;

        // 判斷是否有 keyword 或 tag 參數
        if (query != null) {
            if (query.startsWith("keyword=")) {
                keyword = query.substring(8); // 提取關鍵字
            } else if (query.startsWith("tag=")) {
                tag = query.substring(4); // 提取標籤
            }
        }

        // 根據參數篩選文章
        if (keyword != null) {
            articles = Article.getArticlesByKeyword(keyword); // 根據關鍵字搜索
        } else if (tag != null) {
            articles = Article.getArticlesByTag(tag); // 根據標籤篩選
        } else {
            articles = new ArrayList<>(Article.getAllArticles().values()); // 獲取所有文章
        }

        // 生成 HTML 響應
        StringBuilder response = new StringBuilder("<html><body><h1>Articles</h1><ul>");
        for (Article article : articles) {
            response.append("<li><a href=\"/article/").append(article.getId())
                    .append("\">").append(article.getTitle()).append("</a> - [").append(article.getTag()).append("]</li>");
        }
        response.append("</ul>");

        // 添加標籤篩選選項
        response.append("<h2>Filter by Tag</h2>")
                .append("<a href=\"/articles?tag=最新科技趨勢\">最新科技趨勢</a><br>")
                .append("<a href=\"/articles?tag=職涯經驗分享\">職涯經驗分享</a><br>")
                .append("<a href=\"/articles?tag=課外閱讀推薦\">課外閱讀推薦</a><br>");

        // 添加返回首頁的選項
        response.append("<a href=\"/\">Back to home</a></body></html>");

        // 發送 HTTP 響應
        exchange.sendResponseHeaders(200, response.toString().getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.toString().getBytes());
        }
    }
}
