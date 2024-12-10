public class Student {
    private int userID;
    private String name;
    private String emailAddress;
    private String phoneNumber;

    // Constructors, Getters, and Setters
    public Student(int userID, String name, String emailAddress, String phoneNumber) {
        this.userID = userID;
        this.name = name;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Methods
    public void login() {
        // Login logic here
    }

    public void browseInfo() {
        // Browse articles logic here
    }

    public void saveNote(Note note) {
        // Save note logic here
    }

    public void giveFeedback(Feedback feedback) {
        // Submit feedback logic here
    }
}
