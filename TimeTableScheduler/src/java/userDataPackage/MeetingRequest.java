package userDataPackage;

import java.sql.Time;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import timeTablePackage.EventPriority;
import timeTablePackage.EventTime;

/**
 * A javaBean for handling requests to add a meeting
 */
public final class MeetingRequest extends userRequest{
    private Date date = new Date();//initialise to todays date
    private String meetingName = "";
    private String venue = "";
    private Time time;
    private String description;
    private EventPriority priority;
        
    /**
     * Default constructor
     */
    public MeetingRequest(){ }
    
    /**
     * validates the user input for the date, and sets it if valid, creating an error message otherwise.
     * @param date the date string in HTML's format dd/MM/yyyy, inputted by the user
     */
    public void setDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.date = sdf.parse(date);
        }
        catch (ParseException e) {
            System.err.println("Invalid Date: " + date);
            this.addError("Invalid Date: " + date);
        }
    }
    /**
     * Sets the Meeting name from the form data
     * @param meetingName the name of the meeting
     */
    public void setMeetingName(String meetingName){
        this.meetingName = meetingName;
    }
    /**
     * sets the venue in which the meeting is to take place.
     * @param venue the venue from the form
     */
    public void setVenue(String venue){
        this.venue = venue;
    }
    
    public void setTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        try {
            this.time = (Time)(sdf.parse(time));
        }
        catch (ParseException e) {
            System.err.println("Invalid Time: " + time);
            this.addError("Invalid Time: " + time);
        }
    }
    
    public void setPriority(int priorityLevel) {
        priority = EventPriority.convertToEventPriority(priorityLevel);
        if (priority == null) {
            System.err.println("Invalid priority: " + priorityLevel);
            this.addError("Not a vaild priority");
        }
    }
    
    public void setDescription(String description) {
        if (description == null) {
            addError("Your description is incorrect.");
        } else {
            this.description = description;
        }
    }
    
    /**
     * gets the date
     * @return the date in dd/mm/yyyy format as a string
     */
    public String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.date);
    }
    
    public String getMeetingName(){
        return this.meetingName;
    }
    
    public String getVenue(){
        return this.venue;
    }
    
    public String getTime() {
        return time.toString();
    }
    
    public String getPriority() {
        return priority.getEventOfPriority();
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Creates a meeting and inserts it into the database
     * 
     * @param room Location for the meeting
     * @param description Description of the meeting
     * @param priority Priority level of the meeting compared to existing meetings
     * @param organiser User ID of the user that organised the meeting
     * @param date Date the meeting is held
     * @param time Time the meeting is held
     * @return True if the meeting was created successfully
     */
    public boolean createMeeting() {
        return executeDbQuery("INSERT INTO Meeting (date, time, room, description, priority, organiser) "
                + "VALUES ("+ date.toString() + "\", \""+ time.toString() + "\", \"" + venue + "\", \"" 
                + description + "\", " + priority + ", \"" + getUser().getUserID() + "\");");
    }
    
    /**
     * Join this user to an existing meeting
     * 
     * @param meetingID The ID of the meeting to be joined
     * @return True if joined to the meeting successfully
     */
    public boolean joinMeeting(int meetingID) {
        return executeDbQuery("INSERT INTO InGroup (uid, mid) "
                + "VALUES (" + getUser().getUserID() + "\", " + meetingID + ");");
    }
}