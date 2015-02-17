/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.Date;
import java.sql.Time;

/**
 * Represents a specific time slot in timetable
 *
 * @author John O Riordan
 */
public class TimeSlot {
    private Time time;
    private int dayOfWeek;
    private int[] events;
    private int totalPriority;
    private boolean suggested;
    
    public TimeSlot(Time time, int dayOfWeek) {
        this.time = time;
        this.dayOfWeek = dayOfWeek;
        this.events = new int[EventPriority.highestPriority + 1];
        this.totalPriority = 0;
    }
    
    public TimeSlot() {
        this.time = null;
        this.dayOfWeek = -1;
        this.events = new int[EventPriority.highestPriority + 1];
        this.totalPriority = 0;
    }

    /**
     * Returns the time for this timeslot
     * 
     * @return Time
     */
    public Time getTime() {
        return time;
    }

    /**
     * Returns the day of the week for this timeslot
     * For example Monday timeslot will return 1
     * 
     * @return index for the day of the week
     */
    public int getDayOfWeek() {
        return dayOfWeek;
    }
    
    /**
     * Set if this timeslot is a suggested timeslot for a meeting
     * 
     * @param suggested True if suggested
     */
    public void setSuggested(boolean suggested) {
        this.suggested = suggested;
    }
    
    /**
     * Returns if the timeslot was suggested to hold a meeting
     * 
     * @return True if suggested
     */
    public boolean isSuggested() {
        return suggested;
    }
    
    /**
     * Total priority for all events scheduled during this timeslot
     * 
     * @return total priority
     */
    public int getTotalPriority() {
        return totalPriority;
    }
    
    /**
     * Increments the number of events with this priority value
     * 
     * @param priority Priority of the event scheduled
     */
    public void addPriority(int priority) {
        if (priority >= 0 && priority < events.length) {
            events[priority] += 1;
            totalPriority += priority;
        }
    }
    
    /**
     * Returns the number of lectures in this timeslot
     * 
     * @return Number of lectures
     */
    public int numLectures() {
        return events[EventPriority.LECTURE.getPriority()];
    }
    
    /**
     * Returns the number of practicals in this timeslot
     * 
     * @return Number of practicals
     */
    public int numPracticals() {
        return events[EventPriority.PRACTICAL.getPriority()];
    }
    
    /**
     * Returns the number of meetings in this timeslot
     * 
     * @return Number of meeting
     */
    public int numMeetings() {
        return events[EventPriority.MEETING.getPriority()];
    }
    
    /**
     * Returns the HTML representation of this timeslot that would display in
     * an HTML table
     * 
     * @return HTML td element
     */
    public String printTableCell() {
        if (totalPriority > 0) {
            return "<td class=\"priority-" + totalPriority + "\"" 
                   + (isSuggested() ? " class=\"suggested-timeslot\">" : " >")
                   + "Lecture: " + numLectures() + " \n"
                   + "Practical: " + numPracticals() + " \n"
                   + "Meeting: " + numMeetings() + "</td>";
        } else {
            return "<td" + (isSuggested() ? " class=\"suggested-timeslot\">" : " >") + "</td>";
        }
    }
    
    /**
     * Returns a string that represents the event scheduled in this timeslot with
     * the highest priority.
     * This is used when displaying the event in the HTML table
     * 
     * @return String to represent the highest priority event
     */
    private String highestPriorityEvent() {
        return ((numLectures() > 0) ? EventPriority.LECTURE.getEventOfPriority() : 
               ((numPracticals() > 0) ? EventPriority.PRACTICAL.getEventOfPriority() : 
               ((numMeetings() > 0) ? EventPriority.MEETING.getEventOfPriority() : "")));
    }
     
    @Override
    public String toString() {
        return "Lecture: " + numLectures() + "\n"
               + "Practical: " + numPracticals() + "\n"
               + "Meeting: " + numMeetings();
    }
}
