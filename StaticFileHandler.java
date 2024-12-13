
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticFileHandler implements HttpHandler {
    private static final String STATIC_DIR = "C:\\Users\\a\\Desktop\\SAD project"; // 指向你的專案目錄


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/")) {
                path = "/index.html"; // 預設加載 index.html
            }

            Path filePath = Paths.get(STATIC_DIR + path);
            if (Files.exists(filePath)) {
                byte[] content = Files.readAllBytes(filePath);
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