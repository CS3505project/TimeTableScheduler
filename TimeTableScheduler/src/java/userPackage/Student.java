/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

}
