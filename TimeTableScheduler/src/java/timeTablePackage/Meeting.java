/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.Date;
import java.sql.Time;

/**
 * Represent a meeting in the timetable
 * 
 * @author John O Riordan
 */
public class Meeting extends Event {
    private String description;
    private int priority;
    private String organiser;

    public Meeting(String meetingID, Date date, Time time, String room, 
                   String description, int priority, String organiser) {
        super(meetingID, date, time, room);
        this.description = description;
        this.priority = priority;
        this.organiser = organiser;
    }

    @Override
    public String toString() {
        // To-Do
        return "Meeting " + this.getTime().toString();
    }
    
    @Override
    public EventType getEventType() {
        return EventType.MEETING;
    }
    
    @Override
    public String displayTableHTML() {
        return "<td class=\"meeting\">" 
               + toString() + "</td>";
    }

    /**
     * Returns the description for this meeting
     * 
     * @return The meeting description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description for this meeting
     * 
     * @param description Description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the priority of this event
     * 
     * @return Meeting priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority of the meeting
     * 
     * @param priority Priority of the meeting
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Returns the user ID of the organiser for this event
     * 
     * @return Organiser user ID
     */
    public String getOrganiser() {
        return organiser;
    }

    /**
     * Sets the user ID for this meetings organiser
     * 
     * @param organiser Organiser user ID
     */
    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }
}
