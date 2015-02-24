package userDataPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import timeTablePackage.EventPriority;
import toolsPackage.Database;
import toolsPackage.Validator;

/**
 * A javaBean for handling requests to add a meeting
 */
public final class MeetingRequest extends UserRequest{
    private Date date = new Date();//initialise to todays date
    private String meetingName = "";
    private String venue = "";
    private Time time;
    private String description = "";
    private EventPriority priority = EventPriority.MEETING;
        
    /**
     * Default constructor
     */
    public MeetingRequest(){ 
    }
    
    /**
     * validates the user input for the date, and sets it if valid, creating an error message otherwise.
     * @param date the date string in HTML's format dd/MM/yyyy, inputted by the user
     */
    public void setDate(String date) {
        if (this.errorInString(date)) {
            this.addError("Error in date");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.date = sdf.parse(date);
        }
        catch (ParseException e) {
            System.err.println("Invalid Date: " + date);
            this.addError("Invalid Date: " + date);
        }
        this.setDataEntered(true);
    }

    /**
     * sets the venue in which the meeting is to take place.
     * @param venue the venue from the form
     */
    public void setVenue(String venue){
        if (this.errorInString(venue)) {
            this.addError("Venue is incorrect.");
        } else {
            this.venue = Validator.escapeJava(venue);
        }
        this.setDataEntered(true);
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
        this.setDataEntered(true);
    }
    
    /**
     * Sets the priority of the event
     * @param priorityLevel priority level
     */
    public void setPriority(String priorityLevel) {
        priority = EventPriority.convertToEventPriority(priorityLevel);
        if (priority == null) {
            System.err.println("Invalid priority: " + priorityLevel);
            this.addError("Not a vaild priority");
        }
        this.setDataEntered(true);
    }
    
    /**
     * Sets the description
     * @param description description
     */
    public void setDescription(String description) {
        if (this.errorInString(description)) {
            addError("Your description is incorrect.");
        } else {
            this.description = Validator.escapeJava(description);
        }
        this.setDataEntered(true);
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
     * Gets the venue for the meeting
     * @return venue
     */
    public String getVenue(){
        return Validator.unescapeJava(this.venue);
    }
    
    /**
     * Returns the time for the meeting
     * @return In format hh:mm:ss
     */
    public String getTime() {
        return (time == null ? "" : time.toString());
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
        return Validator.unescapeJava(description);
    }
    
    /**
     * Creates a meeting and inserts it into the database
     * 
     * @return True if the meeting was created successfully
     */
    public boolean createMeeting() {
        boolean result = false;
        if (this.isValid()) {
            System.out.println("here");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String meetingDate = format.format(date);
            Database db = Database.getSetupDatabase();

            result = db.insert("INSERT INTO Meeting (date, time, room, description, priority, organiser_uid) "
                            + "VALUES (\""+ meetingDate + "\", \""+ time.toString() + "\", \"" + venue + "\", \"" 
                            + description + "\", " + priority.getPriority() + ", " + getUser().getUserID() + ");");
            
            int meetingID = db.getPreviousAutoIncrementID("Meeting");
            if (result) {
                result = db.insert("INSERT INTO HasMeeting (uid, mid) VALUES (" + getUser().getUserID() + ", " + meetingID + ");");
            }
            
            date = new Date();//initialise to todays date
            meetingName = "";
            venue = "";
            time = null;
            description = "";
            priority = EventPriority.MEETING;
            this.setDataEntered(false);
            this.clearErrors();
            
            db.close();
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
            result = insertDbQuery("INSERT INTO HasMeeting (uid, mid) "
                        + "VALUES (" + getUser().getUserID() + "\", " + meetingID + ");");
        }
        return result;
    }
}