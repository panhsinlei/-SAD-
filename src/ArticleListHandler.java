package src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ArticleListHandler implements HttpHandler {
    private static final Path TEMPLATE_DIR = Paths.get("static"); // 定義模板目錄的相對路徑

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

        // 將文章轉換為 JSON 格式
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

        // 加載模板文件並插入文章數據
        Path templatePath = TEMPLATE_DIR.resolve("articles.html").normalize(); // 確保路徑安全
        if (Files.exists(templatePath)) {
            String response = new String(Files.readAllBytes(templatePath), "UTF-8");
            response = response.replace("</ul>", 
                "</ul><script>const articles = " + json + ";const ul = document.getElementById('article-list');" +
                "articles.forEach(a => { const li = document.createElement('li'); li.innerHTML = `<a href='/article/${a.id}'>${a.title}</a> - [${a.tag}]`; ul.appendChild(li); });</script>");
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.getBytes("UTF-8").length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes("UTF-8"));
            }
        } else {
            // 模板文件未找到
            exchange.sendResponseHeaders(404, -1);
        }
    }
}
