package backend.model;

public class Favorite {
    private int userId;
    private int articleId;

    public Favorite(int userId, int articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }

    public int getUserId() {
        return userId;
    }

    public int getArticleId() {
        return articleId;
    }

    public String toJson() {
        return String.format("{\"userId\":%d,\"articleId\":%d}", userId, articleId);
    }
}
