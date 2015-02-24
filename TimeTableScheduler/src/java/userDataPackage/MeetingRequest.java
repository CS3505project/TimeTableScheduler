package userDataPackage;

import java.sql.Time;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import timeTablePackage.EventPriority;
import toolsPackage.StringEscapeUtils;

/**
 * A javaBean for handling requests to add a meeting
 */
public final class MeetingRequest extends UserRequest{
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
    public void setDate(String date) {
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
        if (this.errorInString(meetingName)) {
            this.meetingName = StringEscapeUtils.escapeJava(meetingName);
        } else {
            this.addError("Meeting name is inccrrect.");
        }
    }
    /**
     * sets the venue in which the meeting is to take place.
     * @param venue the venue from the form
     */
    public void setVenue(String venue){
        if (this.errorInString(venue)) {
            this.venue = StringEscapeUtils.escapeJava(venue);
        } else {
            this.addError("Venue is incorrect.");
        }
    }
    
    /**
     * Sets the time for the event
     * @param time Time in format hh:mm:ss
     */
    public void setTime(String time) {
        try {
            this.time = Time.valueOf(time);
            Calendar cal = Calendar.getInstance();
            cal.setLenient(false);
            cal.setTime(this.time);
        }
        catch (Exception e) {
            System.err.println("Invalid Time: " + time);
            this.addError("Invalid Time: " + time);
        }
    }
    
    /**
     * Sets the priority of the event
     * @param priorityLevel priority level
     */
    public void setPriority(int priorityLevel) {
        priority = EventPriority.convertToEventPriority(priorityLevel);
        if (priority == null) {
            System.err.println("Invalid priority: " + priorityLevel);
            this.addError("Not a vaild priority");
        }
    }
    
    /**
     * Sets the description
     * @param description description
     */
    public void setDescription(String description) {
        if (description == null) {
            addError("Your description is incorrect.");
        } else {
            this.description = StringEscapeUtils.escapeJava(description);
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
    
    /**
     * Gets the name for the meeting
     * @return Meeting name
     */
    public String getMeetingName(){
        return StringEscapeUtils.unEscapeJava(this.meetingName);
    }
    
    /**
     * Gets the venue for the meeting
     * @return venue
     */
    public String getVenue(){
        return StringEscapeUtils.unEscapeJava(this.venue);
    }
    
    /**
     * Returns the time for the meeting
     * @return In format hh:mm:ss
     */
    public String getTime() {
        return time.toString();
    }
    
    /**
     * Gets the priority for the event
     * @return Event priority
     */
    public String getPriority() {
        return priority.getPriorityName();
    }
    
    /**
     * Gets the description of the event
     * @return description
     */
    public String getDescription() {
        return StringEscapeUtils.unEscapeJava(description);
    }
    
    /**
     * Creates a meeting and inserts it into the database
     * 
     * @return True if the meeting was created successfully
     */
    public boolean createMeeting() {
        boolean result = false;
        if (this.isValid()) {
            result = executeDbQuery("INSERT INTO Meeting (date, time, room, description, priority, organiser) "
                            + "VALUES ("+ date.toString() + "\", \""+ time.toString() + "\", \"" + venue + "\", \"" 
                            + description + "\", " + priority + ", \"" + getUser().getUserID() + "\");");
        }
        return result;
    }
    
    /**
     * Join this user to an existing meeting
     * 
     * @param meetingID The ID of the meeting to be joined
     * @return True if joined to the meeting successfully
     */
    public boolean joinMeeting(int meetingID) {
        boolean result = false;
        if (this.isValid()) {
            result = executeDbQuery("INSERT INTO InGroup (uid, mid) "
                        + "VALUES (" + getUser().getUserID() + "\", " + meetingID + ");");
        }
        return result;
    }
}