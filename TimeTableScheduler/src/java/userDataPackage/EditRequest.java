package userDataPackage;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import timeTablePackage.Day;
import timeTablePackage.Event;
import timeTablePackage.EventPriority;
import timeTablePackage.EventTime;
import timeTablePackage.Meeting;
import timeTablePackage.TimeTable;
import toolsPackage.Database;
import toolsPackage.Validator;

/**
 * A javaBean for handling requests to add a meeting
 */
public final class EditRequest extends UserRequest{
    private Date date = new Date();//initialise to todays date
    private Time time;
    private String description = "";
    private String venue = "";
    private String meetingId = "";
    private Meeting meeting;
    private TimeTable timeTable;
    
    private boolean setup = false;
    
    public static final String TIME_FORMAT = "HH:mm:ss";
            
    /**
     * Default constructor
     */
    public EditRequest(){ 
        initialiseErrorArray(4);
        date = new Date();//initialise to todays date
        venue = "";
        time = null;
        meetingId = "";
        description = "";
    }
     
    public boolean isSetup() {
        return setup;
    }
    
    public boolean isValidEvent(Event event, String userId) {
        boolean valid = false;
        Database db = Database.getSetupDatabase();
        if (event != null) {
            switch (event.getEventType()) {
                case MEETING:
                    ResultSet result = db.select("SELECT * FROM MEETING WHERE meetingid = " + event.getEventID() + " AND organiser_id = " + userId + ");");

                    if (db.getNumRows(result) > 0) {
                        valid = true;
                    }
                    break;
            }
        }
        return valid;
    }
    
    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }
     
    private void resetForm() {
        date = new Date();
        venue = "";
        description = "";
        time = null;
        meetingId = "";
        clearErrors();
    }
    
    public void setup(String date, String time) {
        this.setup = true;
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateFormat.parse(date);
            this.date = java.sql.Date.valueOf(date);
        } catch (Exception ex) {
            this.setup = false;
            System.out.println("her 1");
        }
        
        dateFormat.applyPattern(TIME_FORMAT);
        try {
            dateFormat.parse(time);
            this.time = Time.valueOf(time);
        } catch (Exception ex) {
            this.setup = false;
            System.out.println("her 2");
        }
        System.out.println("her 3");
        if (setup) {
            System.out.println("herer");
            dateFormat.applyPattern("k");
            int timeIndex = Integer.parseInt(dateFormat.format(this.time));
            dateFormat.applyPattern("EEEE");
            int dayIndex = Day.convertToDay(dateFormat.format(this.date)).getIndex();
            // store one event
            meeting = (Meeting)timeTable.getEvents(dayIndex, timeIndex).get(0);
            
            this.date = meeting.getDate();
            this.description = meeting.getDescription();
            this.time = meeting.getTime();
            this.meetingId = meeting.getEventID();
            this.venue = meeting.getLocation();
        }
    }
    
    /**
     * validates the user input for the date, and sets it if valid, creating an error message otherwise.
     * @param date the date string in HTML's format dd/MM/yyyy, inputted by the user
     */
    public void setDate(String date) { 
        if (this.errorInString(date)) {
            this.addErrorMessage(0, "Invalid date entered.");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                this.date = sdf.parse(date);
                setValidData(0, true);
            }
            catch (ParseException e) {
                System.err.println("Invalid Date: " + date);
                this.addErrorMessage(0, "Invalid Date: " + date);
            }
        }
    }

    /**
     * sets the venue in which the meeting is to take place.
     * @param venue the venue from the form
     */
    public void setVenue(String venue){
        if (this.errorInString(venue)) {
            this.addErrorMessage(1, "Venue is incorrect.");
        } else {
            this.venue = Validator.escapeJava(venue);
            setValidData(1, true);
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
            setValidData(2, true);
        }
        catch (Exception e) {
            System.err.println("Invalid Time: " + time);
            this.addErrorMessage(2, "Invalid Time: " + time);
        }
    }
    
    /**
     * Sets the description
     * @param description description
     */
    public void setDescription(String description) {
        if (this.errorInString(description)) {
            addErrorMessage(3, "Your description is incorrect.");
        } else {
            this.description = Validator.escapeJava(description);
            setValidData(3, true);
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
     * Gets the description of the event
     * @return description
     */
    public String getDescription() {
        return Validator.unescapeJava(description);
    }
    
    public boolean checkConflict() {
        return timeTable.conflictWithEvents(date, time, 1, EventPriority.MEETING.getPriority());
    }
    
    /**
     * Creates a meeting and inserts it into the database
     * 
     * @return True if the meeting was created successfully
     */
    public boolean editEvent() {        
        if (isValid() && isSetup() && isValidEvent(meeting, getUser().getUserID())) {
            boolean result = true;
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String meetingDate = format.format(date);
            
            Database db = Database.getSetupDatabase();
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.time);
            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
            result = result && db.insert("UPDATE Meeting (date, time, room, description, priority, organiser_uid) "
                                        + "VALUES (\""+ meetingDate + "\", \"" + timeFormat.format(cal.getTime()) + "\", \"" + venue + "\", \"" 
                                        + description + "\", " + EventPriority.MEETING.getPriority() + ", " + getUser().getUserID() + ");");
            
            resetForm();
            setup = false;
            setFormLoaded(false);
            db.close();
            return result;
        } else {
            return false;
        }
    }
}