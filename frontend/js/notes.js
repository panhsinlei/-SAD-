document.addEventListener("DOMContentLoaded", () => {
    const notesList = document.getElementById("notes-list");

    // 模擬筆記資料
    const notes = ["筆記 1", "筆記 2", "筆記 3"];
    notes.forEach(note => {
        const noteElement = document.createElement("div");
        noteElement.textContent = note;
        notesList.appendChild(noteElement);
    });
});
