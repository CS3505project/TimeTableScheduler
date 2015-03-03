package userPackage;

/**
 * Lecturer system user
 * 
 * @author John O Riordan
 */
public final class Lecturer extends User {
    private String lecturerID;
    private String title;

    public Lecturer(String lecturerID, String email, String title, String firstName, String surName, String userID) {
        super(email, firstName, surName, userID);
        this.lecturerID = lecturerID;
        this.title = title;
    }
    
    @Override
    public UserType getUserType() {
        return UserType.LECTURER;
    }

    /**
     * Returns the title of the lecturer
     * 
     * @return lecturer title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the lecturer
     * 
     * @param title lecturer title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the lecturer ID
     * 
     * @return lecturer ID
     */
    public String getLecturerID() {
        return lecturerID;
    }

    /**
     * Sets the lecturer ID
     * 
     * @param lecturerID lecturer ID
     */
    public void setLecturerID(String lecturerID) {
        this.lecturerID = lecturerID;
    }
    
}
