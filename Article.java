
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
        int idCounter = 1;

        // 最新科技趨勢
        articles.put(idCounter++, new Article(idCounter, "AI Revolution", "Exploring the future of artificial intelligence.", "最新科技趨勢"));
        articles.put(idCounter++, new Article(idCounter, "Blockchain Basics", "Understanding how blockchain works.", "最新科技趨勢"));
        articles.put(idCounter++, new Article(idCounter, "Quantum Computing", "Introduction to quantum computing and its applications.", "最新科技趨勢"));

        // 職涯經驗分享
        articles.put(idCounter++, new Article(idCounter, "Internship Tips", "How to make the most of your internship.", "職涯經驗分享"));
        articles.put(idCounter++, new Article(idCounter, "Resume Building", "Crafting a resume that stands out.", "職涯經驗分享"));
        articles.put(idCounter++, new Article(idCounter, "Networking Strategies", "Building connections in the tech industry.", "職涯經驗分享"));

        // 課外閱讀推薦
        articles.put(idCounter++, new Article(idCounter, "Clean Code", "A must-read book for software developers.", "課外閱讀推薦"));
        articles.put(idCounter++, new Article(idCounter, "The Pragmatic Programmer", "Tips and tricks for becoming a better programmer.", "課外閱讀推薦"));
        articles.put(idCounter++, new Article(idCounter, "Introduction to Algorithms", "Comprehensive guide to algorithms.", "課外閱讀推薦"));
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