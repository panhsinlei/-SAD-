import java.util.Date;

public class Note {
    private int noteID;
    private String content;
    private Date createdDate;
    private int userID;

    // Constructors, Getters, and Setters
    public Note(int noteID, String content, Date createdDate, int userID) {
        this.noteID = noteID;
        this.content = content;
        this.createdDate = createdDate;
        this.userID = userID;
    }

    public int getNoteID() {
        return noteID;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public int getUserID() {
        return userID;
    }

    // Methods
    public void saveNote() {
        // Save note logic here
    }

    public void editNote() {
        // Edit note logic here
    }

    public void deleteNote() {
        // Delete note logic here
    }
}
