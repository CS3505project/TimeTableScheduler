/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.Date;
import java.sql.Time;
import userPackage.User;

/**
 * Represent a meeting in the timetable
 * 
 * @author John O Riordan
 */
public class Meeting extends Event {
    private String description;
    private int priority;
    private User organiser;

    public Meeting(String meetingID, Date date, Time time, String room, String description, int priority, User organiser) {
        super(meetingID, date, time, room);
        this.description = description;
        this.priority = priority;
        this.organiser = organiser;
    }
    
    public Meeting(String meetingID, Date date, Time time, String room) {
        super(meetingID, date, time, room);
    }
    
    @Override
    public String toString() {
        // To-Do
        return "";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public User getOrganiser() {
        return organiser;
    }

    public void setOrganiser(User organiser) {
        this.organiser = organiser;
    }
}
