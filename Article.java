public class Article {
    private int articleID;
    private String title;
    private String content;
    private String category;
    private String author;

    // Constructors, Getters, and Setters
    public Article(int articleID, String title, String content, String category, String author) {
        this.articleID = articleID;
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
    }

    public int getArticleID() {
        return articleID;
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

    // Methods
    public void selectArticle() {
        // Select article logic here
    }

    public void displayContent() {
        // Display article content logic here
    }

    public void handleError() {
        // Handle errors logic here
    }
}
