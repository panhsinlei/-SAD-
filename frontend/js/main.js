document.addEventListener("DOMContentLoaded", () => {
    const categories = ["資管基礎", "軟體工程", "AI 基礎"];
    const container = document.getElementById("categories");

    categories.forEach(category => {
        const categoryElement = document.createElement("div");
        categoryElement.textContent = category;
        container.appendChild(categoryElement);
    });
});
