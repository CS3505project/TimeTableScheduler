/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class PracticalRequest extends UserRequest{
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
    public PracticalRequest(){ 
        initialiseErrorArray(4);
        startDate = new java.util.Date();//initialise to todays date
        endDate = new java.util.Date();//initialise to todays date
        venue = "";
        time = null;
    }
    
    public void print() {
        System.out.println("date: " + startDate + "venue: " + venue + "time: " + time);
    }
     
    public boolean isSetup() {
        return setup;
    }
    
    public List<String> getUsersInvolved() {
        return usersInvolved;
    }
    
    public void setTimeTable(TimeTable timeTable) {
        this.timeTable = timeTable;
    }
    
    public boolean checkConflict() {
        return timeTable.conflictWithEvents(startDate, time, duration, EventPriority.MEETING.getPriority());
    }
    
    private void resetForm() {
        startDate = new java.util.Date();
        endDate = new java.util.Date(); // initialise to todays date
        venue = "";
        time = null;
        usersInvolved = new ArrayList<String>();
        clearErrors();
    }

    public int getDuration() {System.out.println("error: 7");
        return duration;
    }
    
     public void setup(String moduleCode, String duration, String semester) { System.out.println("error: 8");
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
    public void setStartDate(String date) {System.out.println("error: 9.3");
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
     * sets the venue in which the meeting is to take place.
     * @param venue the venue from the form
     */
    public void setVenue(String venue){System.out.println("error: 10");
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
    public void setTime(String time) {System.out.println("error: 11");
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
    public void setEndDate(String date) {System.out.println("error: 9.2");
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
    public String getStartDate(){System.out.println("error: 13.2");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.startDate);
    }
    
    /**
     * Gets the venue for the meeting
     * @return venue
     */
    public String getVenue(){System.out.println("error: 14");
        return Validator.unescapeJava(this.venue);
    }
    
    /**
     * Returns the time for the meeting
     * @return In format hh:mm:ss
     */
    public String getTime() {System.out.println("error: 15");
        return (time == null ? "" : time.toString());
    }
    
    /**
     * gets the date
     * @return the date in dd/mm/yyyy format as a string
     */
    public String getEndDate(){System.out.println("error: 13.4");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.endDate);
    }
    
    
    /**
     * Creates a meeting and inserts it into the database
     * 
     * @return True if the meeting was created successfully
     */
    public boolean createPractical() {System.out.println("error: 17");
        print();
        if (isValid() && isSetup()) {
            boolean result = false;
            
            System.out.println("here exe");
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String meetingDate = format.format(startDate);
            
            Database db = Database.getSetupDatabase();

            SimpleDateFormat dateTime = new SimpleDateFormat(DAY);
            int weekDay = Day.convertToDay(dateTime.format(startDate)).getIndex();
            
            Calendar cal = Calendar.getInstance();    
            cal.setTime(this.time);
            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
            for (int i = 0; i < duration; i++) {  
                System.out.println(db.getPreviousAutoIncrementID("Meeting"));
                result = db.insert("INSERT INTO Practical (modulecode, semester, weekday, time, room, startdate, enddate) "
                                    + "VALUES ("+ moduleCode + "\", \""+ semester + "\", " + weekDay + ", \"" 
                                    + timeFormat.format(cal.getTime()) + "\", " + venue + ", \"" + "\", " + startDate.toString() + ", \"" + endDate.toString() + "\");");

                //To-Do add to groups
                
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
    
    public void setUsersInvolved(String moduleCode) {System.out.println("error: 20");
        Database db = Database.getSetupDatabase();
        try {
            ResultSet groupResult = db.select("(SELECT uid " +
                                            "	FROM InGroup " +
                                            "	WHERE	gid IN " +
                                            "		(SELECT gid " +
                                            "		FROM GroupTakesCourse " +
                                            "		WHERE courseid IN " +
                                            "			(SELECT courseid " +
                                            "			FROM ModuleInCourse " +
                                            "			WHERE moduleCode = 'cs3305')));");
            while (groupResult.next()) {
                usersInvolved.add(groupResult.getString("uid"));
            }
        } catch (SQLException ex) {
            System.err.println("Problem getting users involved.");
        }
        db.close();
    }
    
    /**
     * Cancel a practical or lecture for a specific module and adds the 
     * cancellation to the database
     * 
     * @param moduleCode Module of the practical or lecture being cancelled
     * @param date Date of cancellation
     * @param time Time of cancellation
     * @param description Reason for cancellation, optional
     * @return True if cancellation successfully added
     */
    public boolean cancelPractical(String moduleCode, Date date, Time time, String description) {
        return this.insertDbQuery("INSERT INTO Cancellation (modulecode, semester, weekday, time, room, startdate, enddate) "
                + "VALUES ("+ moduleCode + "\", \""+ date.toString() + "\", " + time.toString() + ", \"" + description + "\");");
    }
}
