import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Article {
    private static final Map<Integer, Article> articles = new HashMap<>();

    private final int id;
    private final String title;
    private final String content;
    private final String tag; // 新增標籤屬性

    // 建構子
    public Article(int id, String title, String content, String tag) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tag = tag;
    }

    // 初始化假資料
    static {
        articles.put(1, new Article(1, "Hello World", "This is a test article about the world of programming.", "最新科技趨勢"));
        articles.put(2, new Article(2, "Java Programming", "Learn the basics of Java and how to build your first project.", "課外閱讀推薦"));
        articles.put(3, new Article(3, "Career Tips", "Explore how to excel in your career with tips and tricks.", "職涯經驗分享"));
    }

    // 獲取所有文章
    public static Map<Integer, Article> getAllArticles() {
        return articles;
    }

    // 根據 ID 獲取單篇文章
    public static Article getArticleById(int id) {
        return articles.get(id);
    }

    // 根據標籤篩選文章
    public static List<Article> getArticlesByTag(String tag) {
        List<Article> filteredArticles = new ArrayList<>();
        for (Article article : articles.values()) {
            if (article.tag.equals(tag)) {
                filteredArticles.add(article);
            }
        }
        return filteredArticles;
    }

    // Getter 方法
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTag() {
        return tag;
    }
}
