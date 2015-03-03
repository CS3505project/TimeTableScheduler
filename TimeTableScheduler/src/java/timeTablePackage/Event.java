package timeTablePackage;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

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
     * Used to format the date print the full day name
     */
    public static final String DAY = "EEEE";
    /**
     * Formats the time to display the hour index
     */
    public static final String HOUR_INDEX = "k";

    public Event(String eventID, Date date, Time time, String location) {
        this.eventID = eventID;
        this.date = date;
        this.time = time;
        this.location = location;
    }
    
    @Override
    public abstract String toString();
    
    /**
     * Returns the priority for the event
     * @return event priority
     */
    public abstract EventPriority getEventPriority();
    
    /**
     * Returns the type of event
     * 
     * @return The event type
     */
    public abstract EventType getEventType();
    
    /**
     * Gets the day of the week as a string for this event
     * For example Monday is 1
     * 
     * @return Index for the day
     */
    public int getDayOfWeek() {
        SimpleDateFormat dateTime = new SimpleDateFormat(DAY);
        return Day.convertToDay(dateTime.format(date)).getIndex();
    }
    
    /**
     * Returns the index for the hour the event is scheduled
     * For example 10pm returns 22
     * @return 
     */
    public int getHourIndex() {
        SimpleDateFormat dateTime = new SimpleDateFormat(HOUR_INDEX);
        return Integer.parseInt(dateTime.format(time));
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