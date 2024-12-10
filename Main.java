import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 啟動伺服器
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("伺服器已啟動，請訪問：http://localhost:8080/");

        // 假資料
        List<Article> articles = Arrays.asList(
            new Article(1, "第一篇文章", "這是第一篇文章的內容。", "技術", "John Doe"),
            new Article(2, "第二篇文章", "這是第二篇文章的內容。", "生活", "Jane Smith")
        );

        // 處理客戶端請求
        while (true) {
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // 讀取請求行
            String requestLine = in.readLine();
            if (requestLine == null) continue;

            System.out.println("收到請求：" + requestLine);
            String[] requestParts = requestLine.split(" ");
            String path = requestParts[1]; // 獲取路徑

            // 根據請求路徑返回不同內容
            if (path.equals("/")) {
                // 返回文章列表頁
                sendResponse(out, generateArticleListPage(articles));
            } else if (path.startsWith("/articles/")) {
                // 返回文章詳情頁
                String articleIdStr = path.split("/")[2];
                try {
                    int articleId = Integer.parseInt(articleIdStr);
                    sendResponse(out, generateArticleDetailPage(articleId, articles));
                } catch (NumberFormatException e) {
                    sendResponse(out, "<h1>無效的文章 ID</h1>");
                }
            } else {
                // 返回 404 頁面
                sendResponse(out, "<h1>404 - 找不到頁面</h1>");
            }

            clientSocket.close();
        }
    }

    // 返回 HTTP 響應
    private static void sendResponse(PrintWriter out, String content) {
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html; charset=UTF-8");
        out.println("Content-Length: " + content.getBytes().length);
        out.println();
        out.println(content);
    }
    // 生成文章列表頁面
    private static String generateArticleListPage(List<Article> articles) {
        StringBuilder page = new StringBuilder();
        // HTML 頁面開始
        page.append("<html><head><title>文章列表</title></head><body>");
        page.append("<h1>文章列表</h1>");
        page.append("<ul>");

        // 遍歷文章列表，生成每篇文章的超連結
        for (Article article : articles) {
            page.append("<li><a href=\"/articles/")
                .append(article.getArticleID()) // 插入文章 ID
                .append("\">")
                .append(article.getTitle())    // 插入文章標題
                .append("</a></li>");
        }

        // HTML 頁面結束
        page.append("</ul>");
        page.append("</body></html>");
        return page.toString(); // 返回生成的 HTML 字串
    }
    private static String generateArticleDetailPage(int articleId, List<Article> articles) {
        for (Article article : articles) {
            if (article.getArticleID() == articleId) {
                StringBuilder page = new StringBuilder();
                page.append("<html><head><title>")
                    .append(article.getTitle())
                    .append("</title></head><body>");
                page.append("<h1>").append(article.getTitle()).append("</h1>");
                page.append("<p>").append(article.getContent()).append("</p>");
                page.append("<a href=\"/\">返回文章列表</a>");
                page.append("</body></html>");
                return page.toString();
            }
        }
        // 如果找不到文章，返回 404 頁面
        return "<html><head><title>404 - 找不到文章</title></head><body>" +
               "<h1>404 - 找不到文章</h1>" +
               "<a href=\"/\">返回文章列表</a></body></html>";
    }


  }


