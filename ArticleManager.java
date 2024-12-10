import java.util.ArrayList;
import java.util.List;

public class ArticleManager {
    private List<Article> articles;

    public ArticleManager() {
        this.articles = new ArrayList<>();
    }
    // 添加文章
    public void addArticle(Article article) {
        articles.add(article);
    }
    // 生成文章列表頁面
    public String generateArticleListPage() {
        StringBuilder page = new StringBuilder();
        page.append("<html><head><title>文章列表</title></head><body>");
        page.append("<h1>文章列表</h1><ul>");

        for (Article article : articles) {
            page.append("<li><a href=\"/articles/").append(article.getId()).append("\">")
                    .append(article.getTitle()).append("</a></li>");
        }
        
        page.append("</ul></body></html>");
        return page.toString();
    }
    // 生成文章詳情頁面
    public String generateArticleDetailPage(int articleId) {
        for (Article article : articles) {
            if (article.getId() == articleId) {
                StringBuilder page = new StringBuilder();
                page.append("<html><head><title>").append(article.getTitle()).append("</title></head><body>");
                page.append("<h1>").append(article.getTitle()).append("</h1>");
                page.append("<p>").append(article.getContent()).append("</p>");
                page.append("<a href=\"/\">返回文章列表</a>");
                page.append("</body></html>");
             
                return page.toString();
            }
        }
        return "<html><head><title>404 Not Found</title></head><body><h1>文章不存在</h1><a href=\"/\">返回文章列表</a></body></html>";
    }
}
