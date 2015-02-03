/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userPackage;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author John O Riordan
 */
public class Lecturer extends User {

    public Lecturer(String email, String firstName, String surName, String userID) {
        super(email, firstName, surName, userID);
    }
    
    public boolean addPractical(String moduleCode, String semester, int weekDay, Time time, String room, Date startDate, Date endDate) {
        return this.executeDbQuery("INSERT INTO Practical (modulecode, semester, weekday, time, room, startdate, enddate) "
                + "VALUES ("+ moduleCode + "\", \""+ semester + "\", " + weekDay + ", \"" 
                + time.toString() + "\", " + room + ", \"" + "\", " + startDate.toString() + ", \"" + endDate.toString() + "\");");
    }
    
    public boolean cancelPracticalOrLecture(String moduleCode, Date date, Time time, String description) {
        return this.executeDbQuery("INSERT INTO Practical (modulecode, semester, weekday, time, room, startdate, enddate) "
                + "VALUES ("+ moduleCode + "\", \""+ date.toString() + "\", " + time.toString() + ", \"" + description + "\");");
    }
    
}
