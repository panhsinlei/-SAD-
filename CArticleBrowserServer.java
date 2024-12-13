import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class CArticleBrowserServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        // 註冊處理模組
        server.createContext("/", new StaticFileHandler());
        server.createContext("/articles", new ArticleListHandler());
        server.createContext("/article", new ArticleHandler());

        server.start();
        System.out.println("Server started at http://localhost:" + PORT);
    }
}
