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
import timeTablePackage.EventPriority;
import toolsPackage.Database;
import toolsPackage.Validator;

/**
 * A javaBean for handling requests to add a meeting
 */
public final class MeetingRequest extends UserRequest{
    private Date date = new Date();//initialise to todays date
    private String venue = "";
    private Time time;
    private String description = "";
            
    /**
     * Default constructor
     */
    public MeetingRequest(){ 
        initialiseValidData(4);
    }
     
    private void resetForm() {
        date = new Date();
        venue = "";
        time = null;
        description = "";
        this.clearErrors();
    }
    
    /**
     * validates the user input for the date, and sets it if valid, creating an error message otherwise.
     * @param date the date string in HTML's format dd/MM/yyyy, inputted by the user
     */
    public void setDate(String date) {
        if (this.errorInString(date)) {
            this.addError("Invalid date entered.");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                this.date = sdf.parse(date);
                setValidData(0, true);
            }
            catch (ParseException e) {
                System.err.println("Invalid Date: " + date);
                this.addError("Invalid Date: " + date);
            }
        }
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
            this.addError("Invalid Time: " + time);
        }
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
     * Creates a meeting and inserts it into the database
     * 
     * @return True if the meeting was created successfully
     */
    public boolean createMeeting() {
        boolean result = false;
        
        if (this.isValid()) {
            System.out.println("here");
            
            EventPriority priority = getPriority((String)getRequest().getParameter("priority"));
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String meetingDate = format.format(date);
            Database db = Database.getSetupDatabase();
            result = db.insert("INSERT INTO Meeting (date, time, room, description, priority, organiser_uid) "
                            + "VALUES (\""+ meetingDate + "\", \""+ time.toString() + "\", \"" + venue + "\", \"" 
                            + description + "\", " + priority.getPriority() + ", " + getUser().getUserID() + ");");
            
            List<Integer> usersInMeeting = getUsersToMeet(db, (String)getRequest().getParameter("withType"));
            addUsersToMeeting(db, db.getPreviousAutoIncrementID("Meeting"), usersInMeeting);
            
            this.setActionCompleted(result);
            resetForm();
            db.close();
        }
        return result;
    }
    
    private EventPriority getPriority(String priorityLevel) {
        return EventPriority.convertToEventPriority(priorityLevel);
    }
    
    private List<Integer> getUsersToMeet(Database db, String meetingType) {
        List<Integer> usersInMeeting = new ArrayList<Integer>();
        try {
            switch (meetingType) {
                case "group":
                    getRequest().getParameter("groupID");
                    ResultSet groupResult = db.select("");
                    while (groupResult.next()) {
                        usersInMeeting.add(groupResult.getInt("uid"));
                    }
                    break;
                case "individual":
                    getRequest().getParameter("individualID");
                    ResultSet individualResult = db.select("");
                    while (individualResult.next()) {
                        usersInMeeting.add(individualResult.getInt("uid"));
                    }
                    break;
                default:
                    usersInMeeting.add(Integer.parseInt(getUser().getUserID()));
                    break;
            }
        } catch (SQLException ex) {
            System.err.println("Problem getting users in meeting.");
        }
        return usersInMeeting;
    }
    
    private void addUsersToMeeting(Database db, int meetingID, List<Integer> usersInMeeting) {
        String values = "";
        Iterator<Integer> userIds = usersInMeeting.iterator();
        while (userIds.hasNext()) {
            values += "(" + userIds.next() + ", " + meetingID + ")" + (userIds.hasNext() ? ", " : ";");
        }
        db.insert("INSERT INTO HasMeeting (uid, mid) VALUES " + values);
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