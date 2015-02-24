package userDataPackage;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import toolsPackage.Database;
import userPackage.User;

/** 
 * A generic representation of a user request.
 * The more specific requests will store all the 
 * relevant request variables, validate them etc 
 * and contain methods for executing against the database if the data is valid.
 * @author lam1
 */
public abstract class UserRequest {
    private HttpServletRequest request;
    private userPackage.User user;
    private List<String> errors;
    
    public UserRequest(){
        this.errors = new ArrayList<String>();
    }
    
    public void setValues(HttpServletRequest request, userPackage.User user) {
        this.request = request;
        this.user = user;
    }
    
    /**
     * Returns the user object for this request
     * 
     * @return User
     */
    public User getUser() {
        return user;
    }
    
    /**
     * Returns the actual request object
     * 
     * @return request 
     */
    public HttpServletRequest getRequest() {
        return request;
    }
    
    /**
     * Checks if a string is null or empty.
     * 
     * @param string The string to check
     * @return True if null or empty
     */
    public boolean errorInString(String string) {
        return (string == null || string.equals(""));
    }
    
    /**
     * Add an error message to the list.
     * 
     * @param message Error message
     */
    public void addError(String message) {
        errors.add(message);
    }
    
    /**
     * Returns error descriptions with the user's input
     * 
     * @return a list of the errors, separated by line breaks. 
     */
    public String getErrors(){
        String result = "";
        for(String error : errors){
            result += (error + "<br>");
        }
        return result;
    }
    
    /**
     * Checks if any errors exist
     * 
     * @return True if errors present
     */
    public boolean isValid() {
        return errors.isEmpty();
    }
    
    /**
     * Executes the querys to the database that don't return results
     * 
     * @param insertSQL The sql query to execute
     * @return True if query was successful
     */
    public boolean executeDbQuery(String insertSQL) {
        //create db
        Database db = Database.getSetupDatabase();
        
        if (db.insert(insertSQL)) {
            //close db
            db.close();
            return true;
        }
        db.close();
        return false;
    }
    
}
