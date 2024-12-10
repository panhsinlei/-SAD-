import java.util.Date;

public class Feedback {
    private int feedbackID;
    private String content;
    private int rating;
    private Date createdDate;
    private int userID;

    // Constructors, Getters, and Setters
    public Feedback(int feedbackID, String content, int rating, Date createdDate, int userID) {
        this.feedbackID = feedbackID;
        this.content = content;
        this.rating = rating;
        this.createdDate = createdDate;
        this.userID = userID;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public String getContent() {
        return content;
    }

    public int getRating() {
        return rating;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public int getUserID() {
        return userID;
    }

    // Methods
    public void submitFeedback() {
        // Submit feedback logic here
    }

    public void editFeedback() {
        // Edit feedback logic here
    }

    public void deleteFeedback() {
        // Delete feedback logic here
    }
}
