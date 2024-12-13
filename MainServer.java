import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class MainServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // 靜態文件處理
        server.createContext("/", new StaticFileHandler("test/public"));

        // 文章相關處理
        Article articleService = new Article();
        server.createContext("/upload", articleService::handleUpload);
        server.createContext("/articles", articleService::handleList);
        server.createContext("/article", articleService::handleView);
        server.createContext("/search", articleService::handleSearch);

        server.start();
        System.out.println("Server started at http://localhost:" + PORT);
    }
}
