/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author jjor1
 */
public class Meeting {
    private String meetingID;
    private Date date;
    private Time time;
    private String room;
    private String description;
    private int priority;
    private String organiser;

    public Meeting(String meetingID, Date date, Time time, String room, String description, int priority, String organiser) {
        this.meetingID = meetingID;
        this.date = date;
        this.time = time;
        this.room = room;
        this.description = description;
        this.priority = priority;
        this.organiser = organiser;
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public String getOrganiser() {
        return organiser;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }
}
