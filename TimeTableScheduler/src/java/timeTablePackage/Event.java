/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
    
    /**
     * Format that prints the full name for a day
     */
    public static final String DAY_FORMAT = "EEEE";

    public Event(String eventID, Date date, Time time, String location) {
        this.eventID = eventID;
        this.date = date;
        this.time = time;
        this.location = location;
    }
    
    @Override
    public abstract String toString();
    
    /**
     * Outputs HTML needed to display the type of event in the timetable 
     * on the webpage
     * 
     * @return HTML to display the type of event 
     */
    public abstract String displayTableHTML();
    
    /**
     * Gets the day of the week as a string for this event
     * For example "Monday"
     * 
     * @return Day of the week as a string
     */
    public String getDayOfWeek() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DAY_FORMAT);
        return dateFormat.format(this.getDate());
    }

    /**
     * Returns the ID for this event
     * 
     * @return Event ID
     */
    public String getEventID() {
        return eventID;
    }

    /**
     * Sets the ID to a new value
     * 
     * @param eventID New ID value
     */
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    /**
     * Returns the date for this event
     * 
     * @return The event date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date to a new date
     * 
     * @param date New date for the event
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the time the event is on
     * 
     * @return Time for the event
     */
    public Time getTime() {
        return time;
    }

    /**
     * Sets a new time for the event
     * 
     * @param time New time for the event
     */
    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * Returns the location of the event
     * 
     * @return Location of the event
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the a new location for the event
     * 
     * @param location New location for the event
     */
    public void setLocation(String location) {
        this.location = location;
    }
}