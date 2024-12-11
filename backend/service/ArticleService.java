package backend.service;

import backend.model.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleService {
    private final List<Article> articles = new ArrayList<>();

    public ArticleService() {
        articles.add(new Article(1, "資管基礎概述", "這是一篇介紹資管基礎的文章", "資管基礎", "作者A"));
        articles.add(new Article(2, "軟體工程的重要性", "這是一篇關於軟體工程的重要性的文章", "軟體工程", "作者B"));
    }

    public List<Article> getAllArticles() {
        return articles;
    }

    public Article getArticleById(int id) {
        return articles.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }
}
