/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userPackage;

import java.sql.Date;
import java.sql.Time;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLecturerID() {
        return lecturerID;
    }

    public void setLecturerID(String lecturerID) {
        this.lecturerID = lecturerID;
    }
    
    /**
     * Create a practical  for a specific module and add it the database
     * 
     * @param moduleCode Module code for the practical
     * @param semester Semester the practical is held in
     * @param weekDay Day of the week the practical is on, 1 = Monday, 2 = Tuesday etc.
     * @param time Time of the practical
     * @param room Location for the practical
     * @param startDate Start date of the practical
     * @param endDate End date of the practical
     * @return True if practical was successfully added to the database
     */
    public boolean addPractical(String moduleCode, String semester, int weekDay, Time time, String room, Date startDate, Date endDate) {
        return this.executeDbQuery("INSERT INTO Practical (modulecode, semester, weekday, time, room, startdate, enddate) "
                + "VALUES ("+ moduleCode + "\", \""+ semester + "\", " + weekDay + ", \"" 
                + time.toString() + "\", " + room + ", \"" + "\", " + startDate.toString() + ", \"" + endDate.toString() + "\");");
    }
    
    /**
     * Cancel a practical or lecture for a specific module and adds the 
     * cancellation to the database
     * 
     * @param moduleCode Module of the practical or lecture being cancelled
     * @param date Date of cancellation
     * @param time Time of cancellation
     * @param description Reason for cancellation, optional
     * @return True if cancellation successfully added
     */
    public boolean cancelPracticalOrLecture(String moduleCode, Date date, Time time, String description) {
        return this.executeDbQuery("INSERT INTO Practical (modulecode, semester, weekday, time, room, startdate, enddate) "
                + "VALUES ("+ moduleCode + "\", \""+ date.toString() + "\", " + time.toString() + ", \"" + description + "\");");
    }
    
}
