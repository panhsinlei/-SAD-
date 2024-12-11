package backend.model;

public class Note {
    private int id;
    private int articleId;
    private String content;

    public Note(int id, int articleId, String content) {
        this.id = id;
        this.articleId = articleId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getArticleId() {
        return articleId;
    }

    public String getContent() {
        return content;
    }

    public String toJson() {
        return String.format("{\"id\":%d,\"articleId\":%d,\"content\":\"%s\"}", id, articleId, content);
    }
}
