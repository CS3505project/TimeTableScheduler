package userDataPackage;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import timeTablePackage.Day;
import timeTablePackage.Event;
import timeTablePackage.EventPriority;
import timeTablePackage.Meeting;
import timeTablePackage.TimeTable;
import toolsPackage.Database;
import toolsPackage.Validator;

/**
 * A javaBean for handling requests to edit a meeting
 */
public final class EditRequest extends UserRequest{
    private Date date = new Date();//initialise to todays date
    private Time time;
    private String description = "";
    private String venue = "";
    private String meetingId = "";
    private TimeTable timeTable;
    private List<String> usersInMeeting = new ArrayList<String>();
    private Date originalDate;
    private Time originalTime;
    
    private boolean setup = false;
    
    public static final String DAY = "EEEE";
    public static final String HOUR = "k";
    public static final String TIME_FORMAT = "HH:mm:ss";
            
    /**
     * Default constructor
     */
    public EditRequest(){ 
        initialiseErrorArray(4);
        date = new Date();//initialise to todays date
        venue = "";
        time = null;
        description = "";
    }
    
    public List<String> getUsersInMeeting() {
        return usersInMeeting;
    }
    
    public void setUsersInMeeting() {
        Database db = Database.getSetupDatabase();
        
        ResultSet users = db.select("SELECT uid FROM HasMeeting WHERE mid = " + meetingId + ";");
        try {
            while (users.next()) {
                usersInMeeting.add(users.getString("uid"));
            }
        } catch (SQLException ex) {
        }
        db.close();
    }
     
    /**
     * Checks if the form is setup
     * @return True if setup
     */
    public boolean isSetup() {
        return setup;
    }
    
    /**
     * Checks if the event is valid
     * Only the user that organised the event can edit it
     * @param event Event to edit
     * @param userId User that organised it
     * @return True if the event can be edited
     */
    public boolean isValidEvent(String userId) {
        boolean valid = false;
        Database db = Database.getSetupDatabase();
        ResultSet result = db.select("SELECT * FROM Meeting WHERE meetingid = " + meetingId + " AND organiser_uid = " + userId + ";");

        if (db.getNumRows(result) > 0) {
            valid = true;
        }
        db.close();
        return valid;
    }
    
    /**
     * Sets the timetable to be displayed
     * @param timeTable 
     */
    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }
     
    /**
     * Resets the form after successful edit
     */
    private void resetForm() {
        date = new Date();
        venue = "";
        description = "";
        time = null;
        meetingId = "";
        clearErrors();
    }
    
    /**
     * Sets up the event to be edited
     * @param date Date of event to edit
     * @param time Timt of event to edit
     */
    public void setup(String meetingId) {
        this.setup = true;
        Database db = Database.getSetupDatabase();
        
        this.meetingId = meetingId;
        ResultSet meetingDetails = db.select("SELECT * FROM Meeting WHERE meetingid = " + meetingId + ";");
        try {
            if (db.getNumRows(meetingDetails) == 1) {
                while (meetingDetails.next()) {
                    
                    this.date = meetingDetails.getDate("date");
                    this.originalDate = this.date;
                    this.time = meetingDetails.getTime("time");
                    this.originalTime = this.time;
                    this.description = meetingDetails.getString("description");
                    this.venue = meetingDetails.getString("room");
                }
            } else {
                this.setup = false;
            }
        } catch (SQLException ex) {
        }
        
        setUsersInMeeting();
        db.close();
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
    
    /**
     * Checks if the event will conflict with others after the edit
     * @return true if there is a conflict
     */
    public boolean checkConflict() {
        boolean conflict = true;

        if (this.date.equals(this.originalDate) 
                && this.time.equals(this.originalTime)) {
            conflict = false;
        } else {
            conflict = timeTable.conflictWithEvents(date, time, 1, EventPriority.MEETING.getPriority());
        }
                
        return conflict;
    }
    
    /**
     * Edits the meeting and inserts it into the database
     * 
     * @return True if the meeting was edited successfully
     */
    public boolean editEvent() {        
        if (isValid() && isSetup() && isValidEvent(getUser().getUserID())) {
            boolean result = true;
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String meetingDate = format.format(date);
            
            Database db = Database.getSetupDatabase();
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.time);
            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
            result = result && db.insert("UPDATE Meeting "
                                        + "SET date = \""+ meetingDate + "\", time = \"" + timeFormat.format(cal.getTime()) + "\", room = \"" + venue + "\", description = \"" 
                                        + description + "\", priority = " + EventPriority.MEETING.getPriority() + ", organiser_uid = " + getUser().getUserID() + " "
                                        + "WHERE meetingid = " + meetingId + ";");
            
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