document.addEventListener("DOMContentLoaded", () => {
    const favoritesList = document.getElementById("favorites-list");

    // 模擬收藏資料
    const favorites = ["文章 1", "文章 2", "文章 3"];
    favorites.forEach(favorite => {
        const favoriteElement = document.createElement("div");
        favoriteElement.textContent = favorite;
        favoritesList.appendChild(favoriteElement);
    });
});
