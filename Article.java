import java.util.HashMap;
import java.util.Map;

public class Article {
    private static final Map<Integer, String[]> articles = new HashMap<>();

    static {
        // 初始化假資料
        articles.put(1, new String[]{"Hello World", "This is a test article about the world of programming."});
        articles.put(2, new String[]{"Java Programming", "Learn the basics of Java and how to build your first project."});
        articles.put(3, new String[]{"Advanced Topics", "Explore advanced topics in software development and architecture."});
    }

    // 獲取所有文章
    public static Map<Integer, String[]> getAllArticles() {
        return articles;
    }

    // 根據 ID 獲取單篇文章
    public static String[] getArticleById(int id) {
        return articles.get(id);
    }
}
