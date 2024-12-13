import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Article {
    private final Map<Integer, String[]> articles = new HashMap<>();
    private int articleId = 1;

    // 新增文章
    public void handleUpload(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            String body = new String(exchange.getRequestBody().readAllBytes());
            String[] parts = body.split("&");
            String title = parts[0].split("=")[1];
            String content = parts[1].split("=")[1];

            articles.put(articleId++, new String[]{title, content});
            exchange.getResponseHeaders().set("Location", "/articles");
            exchange.sendResponseHeaders(302, -1);
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }

    // 顯示文章列表
    public void handleList(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            StringBuilder response = new StringBuilder("<html><body><h1>Articles</h1><ul>");
            for (Map.Entry<Integer, String[]> entry : articles.entrySet()) {
                response.append("<li><a href=\"/article/")
                        .append(entry.getKey())
                        .append("\">")
                        .append(entry.getValue()[0])
                        .append("</a></li>");
            }
            response.append("</ul><a href=\"/\">Back to upload page</a></body></html>");

            sendResponse(exchange, response.toString());
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }

    // 顯示單篇文章
    public void handleView(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if (path.startsWith("/article/")) {
            int id = Integer.parseInt(path.substring(9));
            String[] article = articles.get(id);

            if (article != null) {
                String response = "<html><body><h1>" + article[0] + "</h1><p>" + article[1] + "</p>"
                        + "<a href=\"/articles\">Back to article list</a></body></html>";
                sendResponse(exchange, response);
            } else {
                exchange.sendResponseHeaders(404, -1);
            }
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }

    // 搜尋文章
    public void handleSearch(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            String query = exchange.getRequestURI().getQuery();
            String keyword = query != null ? query.split("=")[1].toLowerCase() : "";

            StringBuilder response = new StringBuilder("<html><body><h1>Search Results</h1><ul>");
            for (Map.Entry<Integer, String[]> entry : articles.entrySet()) {
                String title = entry.getValue()[0];
                if (title.toLowerCase().contains(keyword)) {
                    response.append("<li><a href=\"/article/")
                            .append(entry.getKey())
                            .append("\">")
                            .append(title)
                            .append("</a></li>");
                }
            }
            response.append("</ul><a href=\"/\">Back to upload page</a></body></html>");
            sendResponse(exchange, response.toString());
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
