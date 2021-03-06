package userDataPackage;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import messagePackage.Message;
import messagePackage.MessageType;
import timeTablePackage.Day;
import static timeTablePackage.Event.DAY;
import timeTablePackage.EventPriority;
import timeTablePackage.TimeTable;
import toolsPackage.Database;
import toolsPackage.Validator;

/**
 *
 * @author cdol1
 */
public class LectureRequest extends UserRequest{
    private java.util.Date startDate = new java.util.Date();//initialise to todays date
    private String venue = "";
    private Time time;
    private java.util.Date endDate = new java.util.Date();//initialise to todays date
    
    private int semester;
    private int duration;
    private String moduleCode;
    private List<String> usersInvolved = new ArrayList<String>();
    private TimeTable timeTable;
    
    private boolean setup = false;
    
    public static final String TIME_FORMAT = "HH:mm:ss";
    
    /**
     * Default constructor
     */
    public LectureRequest(){ 
        initialiseErrorArray(4);
        startDate = new java.util.Date();//initialise to todays date
        endDate = new java.util.Date();//initialise to todays date
        venue = "";
        time = null;
    }
     
    /**
     * Checks if the request is setup to be executed
     * @return true if setup
     */
    public boolean isSetup() {
        return setup;
    }
    
    /**
     * Gets the list of users affected by the lecture
     * @return 
     */
    public List<String> getUsersInvolved() {
        return usersInvolved;
    }
    
    /**
     * Sets the timetable the lecture is being organised in
     * @param timeTable timetable
     */
    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }
    
    /**
     * Checks if there is a conflict with an existing lecture
     * @return True if there is a conflict
     */
    public boolean checkConflict() {
        return timeTable.conflictWithEvents(startDate, time, duration, EventPriority.LECTURE.getPriority());
    }
    
    /**
     * Resets the form fields
     */
    private void resetForm() {
        startDate = new java.util.Date();
        endDate = new java.util.Date(); // initialise to todays date
        venue = "";
        time = null;
        usersInvolved = new ArrayList<String>();
        clearErrors();
    }

    /**
     * Gets the duration for the lecture
     * @return 
     */
    public int getDuration() {
        return duration;
    }
    
    /**
     * Sets up the request to create a new lecture
     * @param moduleCode module to create a lecture for
     * @param duration duration of the lecture
     * @param semester semester the lecture is scheduled for
     */
    public void setup(String moduleCode, String duration, String semester) { 
        this.setup = true;
        
        try {
            this.semester = Integer.parseInt(semester);
        } catch (Exception ex) {
            System.err.println("Error with semester");
            this.semester = 1;
        }
        
        try {
            this.duration = Integer.parseInt(duration);
        } catch (Exception ex) {
            System.err.println("Error with duration");
            this.duration = 1;
        }
        
        this.moduleCode = Validator.escapeJava(moduleCode);
        
        setUsersInvolved(moduleCode);
    }
    
    /**
     * validates the user input for the date, and sets it if valid, creating an error message otherwise.
     * @param date the date string in HTML's format dd/MM/yyyy, inputted by the user
     */
    public void setStartDate(String date) {
        if (this.errorInString(date)) {
            this.addErrorMessage(0, "Invalid date entered.");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                this.startDate = sdf.parse(date);
                setValidData(0, true);
            }
            catch (ParseException e) {
                System.err.println("Invalid Date: " + date);
                this.addErrorMessage(0, "Invalid Date: " + date);
            }
        }
    }

    /**
     * sets the venue in which the lecture is to take place.
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
     * validates the user input for the date, and sets it if valid, creating an error message otherwise.
     * @param date the date string in HTML's format dd/MM/yyyy, inputted by the user
     */
    public void setEndDate(String date) {
        if (this.errorInString(date)) {
            this.addErrorMessage(3, "Invalid date entered.");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                this.endDate = sdf.parse(date);
                setValidData(3, true);
            }
            catch (ParseException e) {
                System.err.println("Invalid Date: " + date);
                this.addErrorMessage(3, "Invalid Date: " + date);
            }
        }
    }
    
    /**
     * gets the date
     * @return the date in dd/mm/yyyy format as a string
     */
    public String getStartDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.startDate);
    }
    
    /**
     * Gets the venue for the lecture
     * @return venue
     */
    public String getVenue(){
        return Validator.unescapeJava(this.venue);
    }
    
    /**
     * Returns the time for the lecture
     * @return In format hh:mm:ss
     */
    public String getTime() {
        return (time == null ? "" : time.toString());
    }
    
    /**
     * gets the date
     * @return the date in dd/mm/yyyy format as a string
     */
    public String getEndDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.endDate);
    }
    
    
    /**
     * Creates a lecture for the selected module
     * 
     * @return True if the lecture was created successfully
     */
    public boolean createLecture() {
        if (isValid() && isSetup()) {
            boolean result = false;
            
            System.out.println("here exe");
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            
            Database db = Database.getSetupDatabase();

            SimpleDateFormat dateTime = new SimpleDateFormat(DAY);
            int weekDay = Day.convertToDay(dateTime.format(startDate)).getIndex();
            
            Calendar cal = Calendar.getInstance();    
            cal.setTime(this.time);
            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
            for (int i = 0; i < duration; i++) {  
                result = db.insert("INSERT INTO Lecture (modulecode, semester, weekday, time, room, startdate, enddate) "
                                    + "VALUES (\"" + moduleCode + "\", "+ semester + ", " + weekDay + ", \"" 
                                    + timeFormat.format(cal.getTime()) + "\", \"" + venue + "\", \"" + format.format(startDate) + "\", \"" + format.format(endDate) + "\");");

                if (result && usersInvolved.size() > 1) {
                    usersInvolved.remove(getUser().getUserID());
                    Message message = new Message("You have a new lecture", 
                                                  moduleCode, venue, format.format(startDate), timeFormat.format(cal.getTime()), 
                                                  false, getUser().getUserID(), MessageType.LECTURE);
                    message.sendMessage(usersInvolved);
                    usersInvolved.add(getUser().getUserID());
                }
                
                // increment to the next time slot if the meeting is longer 
                // than one hour
                cal.add(Calendar.HOUR, 1);
            }
            
            resetForm();
            setup = false;
            db.close();
            return result;
        } else {
            return false;
        }
    }
    
    /**
     * Retrieves the list of user ids that could be affected by the 
     * creation of the new lecture
     * @param moduleCode module code of the lecture being created
     */
    public void setUsersInvolved(String moduleCode) {
        Database db = Database.getSetupDatabase();
        try {
            ResultSet groupResult = db.select("SELECT uid " +
                                              "	FROM InGroup " +
                                              "	WHERE gid IN " +
                                              "		(SELECT gid " +
                                              "		FROM GroupTakesCourse " +
                                              "		WHERE courseid IN " +
                                              "			(SELECT courseid " +
                                              "			FROM ModuleInCourse " +
                                              "			WHERE moduleCode = \"" + moduleCode + "\"));");
            while (groupResult.next()) {
                usersInvolved.add(groupResult.getString("uid"));
            }
        } catch (SQLException ex) {
            System.err.println("Problem getting users involved.");
        }
        db.close();
    }
    
    /**
     * Cancel a lecture for a specific module and adds the 
     * cancellation to the database
     * 
     * @param moduleCode Module of the practical or lecture being cancelled
     * @param date Date of cancellation
     * @param time Time of cancellation
     * @param description Reason for cancellation, optional
     * @return True if cancellation successfully added
     */
    public boolean cancelLecture(String moduleCode, Date date, Time time, String description) {
        return this.insertDbQuery("INSERT INTO Cancellation (modulecode, semester, weekday, time, room, startdate, enddate) "
                + "VALUES ("+ moduleCode + "\", \""+ date.toString() + "\", " + time.toString() + ", \"" + description + "\");");
    }
}