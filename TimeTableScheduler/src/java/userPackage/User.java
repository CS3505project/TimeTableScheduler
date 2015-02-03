/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userPackage;

import java.sql.Date;
import java.sql.Time;
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
        if (db.insert("INSERT INTO Groups (groupName, groupType) VALUES ("+ groupName + "\", \"" + groupType + "\");")) {
            db.close();
            return true;
        }
        db.close();
        return false;
    }
    
    public boolean joinGroup(int groupID) {
        Database db = new Database();
        db.setupFromPropertiesFile("database.properties");
        if (db.insert("INSERT INTO InGroup (uid, gid) "
                + "VALUES (" + userID + "\", " + groupID + ");")) {
            db.close();
            return true;
        }
        db.close();
        return false;
    }
    
    public boolean createMeeting(String room, String description, int priority, String organiser, Date date, Time time) {
        Database db = new Database();
        db.setupFromPropertiesFile("database.properties");
        if (db.insert("INSERT INTO Meeting (date, time, room, description, priority, organiser) "
                + "VALUES ("+ date.toString() + "\", \""+ time.toString() + "\", \"" + room + "\", \"" + description + "\", " + priority + ", \"" + organiser + "\");")) {
            db.close();
            return true;
        }
        db.close();
        return false;
    }
    
    public boolean joinMeeting(int meetingID) {
        Database db = new Database();
        db.setupFromPropertiesFile("database.properties");
        if (db.insert("INSERT INTO InGroup (uid, mid) "
                + "VALUES (" + userID + "\", " + meetingID + ");")) {
            db.close();
            return true;
        }
        db.close();
        return false;
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
