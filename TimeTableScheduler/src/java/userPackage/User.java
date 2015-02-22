/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userPackage;

import java.sql.Date;
import java.sql.Time;
import toolsPackage.Database;

/**
 * Generic user of the system
 * 
 * @author John O Riordan
 */
public abstract class User {
    private String email;
    private String firstName;
    private String surName;
    private String userID;

    public User(String email, String firstName, String surName, String userID) {
        this.email = email;
        this.firstName = firstName;
        this.surName = surName;
        this.userID = userID;
    }
    
    /**
     * Returns the UserType object for this user
     * 
     * @return Type of user
     */
    public abstract UserType getUserType();

    /**
     * Return email address of this user
     * 
     * @return Email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email address of this user
     * 
     * @param email Email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Return first name of this user
     * 
     * @return First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set first name of this user
     * 
     * @param firstName First name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Return surname of this user
     * 
     * @return Surname
     */
    public String getSurName() {
        return surName;
    }

    /**
     * Set surname of this user
     * 
     * @param surName Surname of the user
     */
    public void setSurName(String surName) {
        this.surName = surName;
    }

    /**
     * Return user ID of this user
     * 
     * @return User ID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Set user ID 
     * 
     * @param userID User's ID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }
}
