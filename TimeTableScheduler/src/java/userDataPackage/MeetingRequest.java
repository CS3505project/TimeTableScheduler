package userDataPackage;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A javaBean for handling requests to add a meeting
 */
public final class MeetingRequest extends userRequest{
    private Date date;
    private String meetingName;
    
    private boolean validDate;
    
    private String[] errors;
    
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
            this.validDate = true;
            this.errors[0]="";
        }
        catch (ParseException e) {
            System.err.println("Invalid Date: " + date);
            this.errors[0]="Invalid Date: " + date;
            this.validDate = false;
        }
    }
}
