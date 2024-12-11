document.addEventListener("DOMContentLoaded", () => {
    const articleTitle = document.getElementById("article-title");
    const articleContent = document.getElementById("article-content");

    // 模擬文章資料
    articleTitle.textContent = "軟體工程實務";
    articleContent.textContent = "這是軟體工程相關的內容...";

    document.getElementById("add-to-favorites").addEventListener("click", () => {
        alert("已加入收藏！");
    });

    document.getElementById("add-note").addEventListener("click", () => {
        document.getElementById("note-section").style.display = "block";
    });

    document.getElementById("save-note").addEventListener("click", () => {
        alert("筆記已儲存！");
    });
});
