package userDataPackage;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

/**
 * A javaBean for handling requests to add a meeting
 */
public final class MeetingRequest extends userRequest{
    private Date date = new Date();//initialise to todays date
    private String meetingName = "";
    private String venue = "";
    
    private ArrayList<String> errors = new ArrayList<>();;
    
    /**
     * Default constructor
     */
    public MeetingRequest(HttpServletRequest request, userPackage.User user){
        super(request, user);
    }
    
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
            this.errors.add("Invalid Date: " + date);
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
    
    /**
     * Checks if there are any errors recorded in the errors ArrayList
     * @return whether or not the data filled out so far is valid 
     */
    public boolean isValid(){
        boolean valid = (this.errors.size() == 0) ? true : false;
        return valid;
    }
}