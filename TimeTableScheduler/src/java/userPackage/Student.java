package userPackage;

/**
 * Student system user
 * 
 * @author John O Riordan
 */
public final class Student extends User {
    private String studentID;
    
    public Student(String studentID, String email, String firstName, String surName, String userID) {
        super(email, firstName, surName, userID);
        this.studentID = studentID;
    }
    
    @Override
    public UserType getUserType() {
        return UserType.STUDENT;
    }

    /**
     * Returns the student's ID
     * 
     * @return Student ID
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * Sets the students ID
     * 
     * @param studentID New student ID
     */
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

}
