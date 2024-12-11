package backend;

import backend.service.ArticleService;
import backend.service.FavoriteService;
import backend.service.NoteService;
import backend.model.Note;
import backend.model.Favorite;

public class Main {
    public static void main(String[] args) {
        ArticleService articleService = new ArticleService();
        NoteService noteService = new NoteService();
        FavoriteService favoriteService = new FavoriteService();

        // 範例：列出所有文章
        System.out.println("所有文章:");
        articleService.getAllArticles().forEach(article -> System.out.println(article.toJson()));

        // 範例：新增筆記
        Note note = new Note(1, 1, "這是筆記內容");
        noteService.addNote(note);
        System.out.println("所有筆記:");
        noteService.getAllNotes().forEach(n -> System.out.println(n.toJson()));

        // 範例：新增收藏
        Favorite favorite = new Favorite(1001, 1);
        favoriteService.addFavorite(favorite);
        System.out.println("所有收藏:");
        favoriteService.getAllFavorites().forEach(f -> System.out.println(f.toJson()));
    }
}
