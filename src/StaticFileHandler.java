package src;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticFileHandler implements HttpHandler {
    private static final Path STATIC_DIR = Paths.get("static"); // 靜態文件相對目錄


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "/index.html"; // 預設加載 index.html
            }

            Path filePath = STATIC_DIR.resolve("." + path).normalize(); // 確保安全地解析路徑
            if (Files.exists(filePath)) {
                String mimeType = Files.probeContentType(filePath);

                // 明確設置 MIME 類型
                if (mimeType == null) {
                    if (filePath.toString().endsWith(".css")) {
                        mimeType = "text/css";
                    } else if (filePath.toString().endsWith(".html")) {
                        mimeType = "text/html";
                    } else if (filePath.toString().endsWith(".js")) {
                        mimeType = "application/javascript";
                    } else {
                        mimeType = "application/octet-stream";
                    }
                }

                // 設置回應頭並處理字符編碼
                byte[] content = Files.readAllBytes(filePath);
                if (mimeType.startsWith("text/")) {
                    mimeType += "; charset=UTF-8"; // 為文本類型添加 UTF-8 編碼
                }
                exchange.getResponseHeaders().set("Content-Type", mimeType);
                exchange.sendResponseHeaders(200, content.length);

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(content);
                }
            } else {
                exchange.sendResponseHeaders(404, -1); // 文件未找到
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // 方法不允許
        }
    }
}