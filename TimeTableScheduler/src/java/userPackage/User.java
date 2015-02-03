/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userPackage;

import toolsPackage.Database;

/**
 *
 * @author John O Riordan
 */
public class User {
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
    
    public boolean createGroup(String groupName, String groupType) {
        Database db = new Database();
        db.setupFromPropertiesFile("database.properties");
        return db.insert("INSERT INTO Groups (groupName, groupType) VALUES ("+ groupName + "\", \"" + groupType + "\");");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
