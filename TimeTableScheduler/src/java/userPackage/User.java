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
     * Create a group for users or modules and add to the database
     * 
     * @param groupName Name of the group
     * @param groupType Type of the group
     * @return True if the group was successfully added to the database
     */
    public boolean createGroup(String groupName, String groupType) {
        return executeDbQuery("INSERT INTO Groups (groupName, groupType) VALUES ("+ groupName + "\", \"" + groupType + "\");");
    }
    
    /**
     * Join this use to an existing group in the database
     * 
     * @param groupID ID of the group the user wants to join
     * @return True if user joined group successfully
     */
    public boolean joinGroup(int groupID) {
        return executeDbQuery("INSERT INTO InGroup (uid, gid) "
                + "VALUES (" + userID + "\", " + groupID + ");");
    }
    
    /**
     * Creates a meeting and inserts it into the database
     * 
     * @param room Location for the meeting
     * @param description Description of the meeting
     * @param priority Priority level of the meeting compared to existing meetings
     * @param organiser User ID of the user that organised the meeting
     * @param date Date the meeting is held
     * @param time Time the meeting is held
     * @return True if the meeting was created successfully
     */
    public boolean createMeeting(String room, String description, int priority, String organiser, Date date, Time time) {
        return executeDbQuery("INSERT INTO Meeting (date, time, room, description, priority, organiser) "
                + "VALUES ("+ date.toString() + "\", \""+ time.toString() + "\", \"" + room + "\", \"" 
                + description + "\", " + priority + ", \"" + organiser + "\");");
    }
    
    /**
     * Join this user to an existing meeting
     * 
     * @param meetingID The ID of the meeting to be joined
     * @return True if joined to the meeting successfully
     */
    public boolean joinMeeting(int meetingID) {
        return executeDbQuery("INSERT INTO InGroup (uid, mid) "
                + "VALUES (" + userID + "\", " + meetingID + ");");
    }
    
    /**
     * Executes the querys to the database that don't return results
     * 
     * @param insertSQL The sql query to execute
     * @return True if query was successful
     */
    public boolean executeDbQuery(String insertSQL) {
        //create db
        Database db = Database.getSetupDatabase();
        
        if (db.insert(insertSQL)) {
            //close db
            db.close();
            return true;
        }
        db.close();
        return false;
    }

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
