package backend.model;

public class Article {
    private int id;
    private String title;
    private String content;
    private String category;
    private String author;

    public Article(int id, String title, String content, String category, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public String getAuthor() {
        return author;
    }

    public String toJson() {
        return String.format(
            "{\"id\":%d,\"title\":\"%s\",\"content\":\"%s\",\"category\":\"%s\",\"author\":\"%s\"}",
            id, title, content, category, author
        );
    }
}
