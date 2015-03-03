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
import messagePackage.Message;
import messagePackage.MessageType;
import timeTablePackage.EventPriority;
import timeTablePackage.EventTime;
import timeTablePackage.TimeTable;
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
    
    private int duration;
    private String groupOrUserId;
    private List<String> usersToMeet = new ArrayList<String>();
    private String meetingType;
    private TimeTable timeTable;
    
    private boolean setup = false;
    
    public static final String TIME_FORMAT = "HH:mm:ss";
            
    /**
     * Default constructor
     */
    public MeetingRequest(){ 
        initialiseErrorArray(4);
        date = new Date();//initialise to todays date
        venue = "";
        time = null;
        description = "";
        usersToMeet = new ArrayList<String>();
    }
     
    public boolean isSetup() {
        return setup;
    }
    
    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }
     
    private void resetForm() {
        date = new Date();
        venue = "";
        description = "";
        time = null;
        usersToMeet = new ArrayList<String>();
        clearErrors();
    }
    
    public List<String> getUsersToMeet() {
        return usersToMeet;
    }
    
    public int getDuration() {
        return duration;
    }
    
     public void setup(String withType, String duration, String individualID, String groupID) {
        this.setup = true;
         
        try {
            this.duration = Integer.parseInt(duration);
        } catch (Exception ex) {
            System.err.println("Error with duration");
            this.duration = 1;
        }
        
        if (this.duration >= EventTime.numHours || this.duration <= 0) {
            this.duration = 1;
        }
        
        if (individualID != null) {
            this.groupOrUserId = Validator.escapeJava(individualID);
        } else if (groupID != null) {
            this.groupOrUserId = Validator.escapeJava(groupID);
        }

        if (errorInString(withType)) {
            this.meetingType = "";
        } else {
            this.meetingType = withType;
        }
        
        setUsersToMeet();
        
        if (usersToMeet.size() < 1) {
            setup = false;
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
        return timeTable.conflictWithEvents(date, time, duration, EventPriority.MEETING.getPriority());
    }
    
    /**
     * Creates a meeting and inserts it into the database
     * 
     * @return True if the meeting was created successfully
     */
    public boolean createMeeting() {        
        if (isValid() && isSetup()) {
            boolean result = true;
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String meetingDate = format.format(date);
            
            Database db = Database.getSetupDatabase();
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(this.time);
            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
            for (int i = 0; i < duration; i++) {  
                result = result && db.insert("INSERT INTO Meeting (date, time, room, description, priority, organiser_uid) "
                                            + "VALUES (\""+ meetingDate + "\", \"" + timeFormat.format(cal.getTime()) + "\", \"" + venue + "\", \"" 
                                            + description + "\", " + EventPriority.MEETING.getPriority() + ", " + getUser().getUserID() + ");");

                String values = getMeetingInsertValues(db.getPreviousAutoIncrementID("Meeting")); 
                if (!values.equals("")) {
                    result = result && db.insert("INSERT INTO HasMeeting (uid, mid) VALUES " + values);
                }
                
                if (result) {
                    Message message = new Message("You have a new meeting", 
                                                  description, venue, meetingDate, timeFormat.format(cal.getTime()), 
                                                  false, getUser().getUserID(), MessageType.MEETING);
                    message.sendMessage(usersToMeet);
                }
                
                // increment to the next time slot if the meeting is longer 
                // than one hour
                cal.add(Calendar.HOUR, 1);
            }
            
            resetForm();
            setup = false;
            setFormLoaded(false);
            db.close();
            return result;
        } else {
            return false;
        }
    }
    
    public void setUsersToMeet() {
        Database db = Database.getSetupDatabase();
        try {
            switch (this.meetingType) {
                case "group":
                    ResultSet groupResult = db.select("SELECT uid " +
                                                    "FROM Student JOIN User " +
                                                    "ON Student.uid = userid " +
                                                    "WHERE Student.uid IN " +
                                                    "	(SELECT uid " +
                                                    "	FROM InGroup " +
                                                    "	WHERE gid = " + groupOrUserId + ") " +
                                                    "UNION " +
                                                    "SELECT uid " +
                                                    "FROM Lecturer JOIN User " +
                                                    "ON Lecturer.uid = userid " +
                                                    "WHERE Lecturer.uid IN " +
                                                    "	(SELECT uid " +
                                                    "	FROM InGroup " +
                                                    "	WHERE gid = " + groupOrUserId + ");");
                    while (groupResult.next()) {
                        usersToMeet.add(groupResult.getString("uid"));
                    }
                    break;
                case "individual":
                    usersToMeet.add(groupOrUserId);
                    usersToMeet.add(getUser().getUserID());
                    break;
                default:
                    usersToMeet.add(getUser().getUserID());
                    break;
            }
        } catch (SQLException ex) {
            System.err.println("Problem getting users in meeting.");
        }
        db.close();
    }
    
    private String getMeetingInsertValues(int meetingID) {
        String values = "";
        Iterator<String> userIds = usersToMeet.iterator();
        while (userIds.hasNext()) {
            values += "(" + userIds.next() + ", " + meetingID + ")" + (userIds.hasNext() ? ", " : ";");
        }
        return values;
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