/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userDataPackage;

import java.sql.Date;
import java.sql.Time;
import javax.servlet.http.HttpServletRequest;
import toolsPackage.Database;

/**
 *
 * @author cdol1
 */
public class PracticalRequest extends userRequest{
    
    public PracticalRequest(HttpServletRequest request, userPackage.User user){
        super(request, user);
    }
    
    /**
     * Inserts the new practical details into the database
     * 
     * @param request Contains the lab details
     * @return True if successfully added
     */
    public boolean addLab(HttpServletRequest request) {        
        Database db = Database.getSetupDatabase();
        
        // insert the lab into the database
        boolean result = db.insert("");
        
        db.close();
        
        return result;
    }
    
    /**
     * Create a practical  for a specific module and add it the database
     * 
     * @param moduleCode Module code for the practical
     * @param semester Semester the practical is held in
     * @param weekDay Day of the week the practical is on, 1 = Monday, 2 = Tuesday etc.
     * @param time Time of the practical
     * @param room Location for the practical
     * @param startDate Start date of the practical
     * @param endDate End date of the practical
     * @return True if practical was successfully added to the database
     */
    public boolean addPractical(String moduleCode, String semester, int weekDay, Time time, String room, Date startDate, Date endDate) {
        return this.executeDbQuery("INSERT INTO Practical (modulecode, semester, weekday, time, room, startdate, enddate) "
                + "VALUES ("+ moduleCode + "\", \""+ semester + "\", " + weekDay + ", \"" 
                + time.toString() + "\", " + room + ", \"" + "\", " + startDate.toString() + ", \"" + endDate.toString() + "\");");
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
    public boolean cancelPracticalOrLecture(String moduleCode, Date date, Time time, String description) {
        return this.executeDbQuery("INSERT INTO Cancellation (modulecode, semester, weekday, time, room, startdate, enddate) "
                + "VALUES ("+ moduleCode + "\", \""+ date.toString() + "\", " + time.toString() + ", \"" + description + "\");");
    }
}
