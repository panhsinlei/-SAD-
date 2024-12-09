// 範例文章資料：資管大學生學習相關內容
const articles = [
    { id: 1, title: "如何提升程式設計能力", category: "程式設計", author: "109403000", content: "介紹學習程式設計的技巧與工具。" },
    { id: 2, title: "資料庫設計入門", category: "資料庫", author: "110403000", content: "了解資料庫基礎，設計實用的資料結構。" },
    { id: 3, title: "如何有效管理專案", category: "專案管理", author: "111403000", content: "學習專案管理工具及實務應用。" },
    { id: 4, title: "網頁設計基礎", category: "網頁設計", author: "112403000", content: "掌握 HTML、CSS 與 JavaScript 基本技能。" }
  ];
  
  // 初始化本地數據存儲
  if (!localStorage.getItem("favorites")) localStorage.setItem("favorites", JSON.stringify([]));
  if (!localStorage.getItem("notes")) localStorage.setItem("notes", JSON.stringify([]));
  if (!localStorage.getItem("comments")) localStorage.setItem("comments", JSON.stringify([]));
  
  /** 首頁分類及文章標題 */
  if (document.getElementById("articles")) {
    const articlesSection = document.getElementById("articles");
    const categories = [...new Set(articles.map(a => a.category))];
  
    categories.forEach(category => {
      const categoryHeader = document.createElement("h2");
      categoryHeader.textContent = category;
      articlesSection.appendChild(categoryHeader);
  
      articles.filter(article => article.category === category).forEach(article => {
        const articleTitle = document.createElement("h3");
        articleTitle.innerHTML = `<a href="article.html?id=${article.id}">${article.title}</a>`;
        articlesSection.appendChild(articleTitle);
      });
    });
  }
  
  /** 文章頁面內容顯示 */
  if (document.getElementById("article-content")) {
    const urlParams = new URLSearchParams(window.location.search);
    const articleId = parseInt(urlParams.get("id"), 10);
    const article = articles.find(a => a.id === articleId);
  
    const articleContent = document.getElementById("article-content");
  
    if (article) {
      articleContent.innerHTML = `
        <h2>${article.title}</h2>
        <p><strong>分類：</strong>${article.category}</p>
        <p><strong>作者：</strong>${article.author}</p>
        <p>${article.content}</p>
        <button onclick="addToFavorites(${article.id})">收藏</button>
        <button onclick="addNote(${article.id})">新增筆記</button>
  
        <div id="comments">
          <h3>留言</h3>
          <textarea id="comment-input" placeholder="輸入您的留言..."></textarea>
          <button onclick="addComment(${article.id})">提交留言</button>
          <ul id="comment-list"></ul>
        </div>
      `;
      loadComments(article.id);
    } else {
      window.location.href = "error.html"; // 跳轉到錯誤頁面
    }
  }

  /** 收藏功能 */
  function addToFavorites(id) {
    const favorites = JSON.parse(localStorage.getItem("favorites"));
    if (!favorites.includes(id)) {
      favorites.push(id);
      localStorage.setItem("favorites", JSON.stringify(favorites));
      alert("已加入收藏");
    } else {
      alert("該文章已在收藏中");
    }
  }
  
  function removeFromFavorites(id) {
    let favorites = JSON.parse(localStorage.getItem("favorites"));
    favorites = favorites.filter(fav => fav !== id);
    localStorage.setItem("favorites", JSON.stringify(favorites));
    alert("已取消收藏");
    location.reload(); // 重新加載頁面以更新收藏列表
  }
  
  /** 收藏頁面 */
  if (document.getElementById("favorites")) {
    const favorites = JSON.parse(localStorage.getItem("favorites"));
    const favoritesSection = document.getElementById("favorites");
  
    if (favorites.length === 0) {
      favoritesSection.innerHTML = "<p>目前沒有收藏的文章。</p>";
    } else {
      favorites.forEach(favId => {
        const article = articles.find(a => a.id === favId);
        if (article) {
          const articleDiv = document.createElement("div");
          articleDiv.innerHTML = `<h3><a href="article.html?id=${article.id}">${article.title}</a></h3>`;
          favoritesSection.appendChild(articleDiv);
        }
      });
    }
  }
  
  /** 新增筆記功能 */
  function addNote(id) {
    const noteContent = prompt("請輸入筆記內容：");
    if (noteContent) {
      const notes = JSON.parse(localStorage.getItem("notes"));
      notes.push({ articleId: id, content: noteContent });
      localStorage.setItem("notes", JSON.stringify(notes));
      alert("筆記已保存");
    }
  }
  
  /** 筆記頁面 */
  if (document.getElementById("notes")) {
    const notes = JSON.parse(localStorage.getItem("notes"));
    const notesSection = document.getElementById("notes");
  
    if (notes.length === 0) {
      notesSection.innerHTML = "<p>目前沒有新增的筆記。</p>";
    } else {
      notes.forEach((note, index) => {
        const article = articles.find(a => a.id === note.articleId);
        const noteDiv = document.createElement("div");
        noteDiv.className = "note";
        noteDiv.innerHTML = `
          <h3>${article.title}</h3>
          <p>${note.content}</p>
          <button onclick="deleteNote(${index})">刪除筆記</button>
        `;
        notesSection.appendChild(noteDiv);
      });
    }
  }
  
  function deleteNote(index) {
    const notes = JSON.parse(localStorage.getItem("notes"));
    notes.splice(index, 1);
    localStorage.setItem("notes", JSON.stringify(notes));
    alert("筆記已刪除");
    location.reload(); // 重新加載頁面以更新筆記列表
  }
  
  /** 留言功能 */
  function addComment(articleId) {
    const commentInput = document.getElementById("comment-input");
    const commentText = commentInput.value.trim();
  
    if (commentText) {
      const comments = JSON.parse(localStorage.getItem("comments"));
      comments.push({ articleId, content: commentText });
      localStorage.setItem("comments", JSON.stringify(comments));
      commentInput.value = ""; // 清空輸入框
      loadComments(articleId); // 重新加載留言
    } else {
      alert("留言內容不能為空！");
    }
  }
  
  function loadComments(articleId) {
    const comments = JSON.parse(localStorage.getItem("comments")).filter(comment => comment.articleId === articleId);
    const commentList = document.getElementById("comment-list");
    commentList.innerHTML = ""; // 清空列表
  
    comments.forEach(comment => {
      const commentItem = document.createElement("li");
      commentItem.textContent = comment.content;
      commentList.appendChild(commentItem);
    });
  }
  