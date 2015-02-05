/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.Date;
import java.sql.Time;

/**
 * Generic event that can be a lecture, practical or meeting.
 * 
 * @author John O Riordan
 */
public abstract class Event {
    private String eventID;
    private Date date;
    private Time time;
    private String location;

    public Event(String eventID, Date date, Time time, String location) {
        this.eventID = eventID;
        this.date = date;
        this.time = time;
        this.location = location;
    }
    
    @Override
    public abstract String toString();

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}