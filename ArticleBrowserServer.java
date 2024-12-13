import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ArticleBrowserServer {
    private static final int PORT = 8080;
    private static final String STATIC_DIR = "test/public";
    private static final Map<Integer, String[]> articles = new HashMap<>();
    private static int articleId = 1;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // 處理靜態文件
        server.createContext("/", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String path = exchange.getRequestURI().getPath();
                if (path.equals("/")) {
                    path = "/index.html";
                }

                Path filePath = Paths.get(STATIC_DIR + path);
                if (Files.exists(filePath)) {
                    byte[] content = Files.readAllBytes(filePath);
                    exchange.sendResponseHeaders(200, content.length);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(content);
                    }
                } else {
                    exchange.sendResponseHeaders(404, -1);
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        });

        // 上傳文章
        server.createContext("/upload", exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String body = new String(is.readAllBytes());
                is.close();

                String[] parts = body.split("&");
                String title = parts[0].split("=")[1];
                String content = parts[1].split("=")[1];

                articles.put(articleId++, new String[]{title, content});

                exchange.getResponseHeaders().set("Location", "/articles");
                exchange.sendResponseHeaders(302, -1);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        });

        // 顯示文章列表
        server.createContext("/articles", exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                StringBuilder response = new StringBuilder("<html><body><h1>Articles</h1><ul>");
                for (Map.Entry<Integer, String[]> entry : articles.entrySet()) {
                    response.append("<li><a href=\"/article/").append(entry.getKey())
                            .append("\">").append(entry.getValue()[0]).append("</a></li>");
                }
                response.append("</ul><a href=\"/\">Back to upload page</a></body></html>");

                exchange.sendResponseHeaders(200, response.toString().getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.toString().getBytes());
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        });

        // 顯示單篇文章
        server.createContext("/article", exchange -> {
            String path = exchange.getRequestURI().getPath();
            if (path.startsWith("/article/")) {
                String articleIdStr = path.substring(9);
                int id = Integer.parseInt(articleIdStr);

                if (articles.containsKey(id)) {
                    String[] article = articles.get(id);
                    String response = "<html><body><h1>" + article[0] + "</h1><p>" + article[1] + "</p>"
                            + "<a href=\"/articles\">Back to article list</a></body></html>";

                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                } else {
                    exchange.sendResponseHeaders(404, -1);
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        });

        // 搜尋文章
        server.createContext("/search", exchange -> {
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

                exchange.sendResponseHeaders(200, response.toString().getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.toString().getBytes());
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        });

        server.start();
        System.out.println("Server started at http://localhost:" + PORT);
    }
}
