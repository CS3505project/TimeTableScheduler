/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Pc
 */
public class TimeSlot {
    private Time time;
    private Date date;
    private int[] events;
    private int totalPriority;
    private boolean suggested;
    
    public TimeSlot(Time time, Date date) {
        this.time = time;
        this.date = date;
        this.events = new int[EventPriority.numPriority];
        this.totalPriority = 0;
    }

    public Time getTime() {
        return time;
    }

    public Date getDate() {
        return date;
    }
    
    public void setSuggested(boolean suggested) {
        this.suggested = suggested;
    }
    
    public boolean isSuggested() {
        return suggested;
    }
    
    public int getTotalPriority() {
        return totalPriority;
    }
    
    public void addPriority(int priority) {
        events[priority] += 1;
    }
    
    public int numLectures() {
        return events[EventPriority.LECTURE.getPriority()];
    }
    
    public int numPracticals() {
        return events[EventPriority.PRACTICAL.getPriority()];
    }
    
    public int numMeetings() {
        return events[EventPriority.MEETING.getPriority()];
    }
    
    public String printTableCell() {
        if (totalPriority > 0) {
            return "<td class=\"" + highestPriorityEvent() + "\"" 
                   + (isSuggested() ? " class=\"suggested-timeslot\">" : " >")
                   + "Lecture: " + numLectures() + "\n"
                   + "Practical: " + numPracticals() + "\n"
                   + "Meeting: " + numMeetings() + "</td>";
        } else {
            return "<td></td>";
        }
    }
    
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
