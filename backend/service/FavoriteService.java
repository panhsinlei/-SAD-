package backend.service;

import backend.model.Favorite;

import java.util.ArrayList;
import java.util.List;

public class FavoriteService {
    private final List<Favorite> favorites = new ArrayList<>();

    public List<Favorite> getAllFavorites() {
        return favorites;
    }

    public void addFavorite(Favorite favorite) {
        favorites.add(favorite);
    }

    public void removeFavorite(int userId, int articleId) {
        favorites.removeIf(f -> f.getUserId() == userId && f.getArticleId() == articleId);
    }
}
