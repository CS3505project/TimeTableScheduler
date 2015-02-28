package userDataPackage;

import java.sql.ResultSet;
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
    private DataEntry data;
    private boolean formLoaded;
    
    public UserRequest(){ }
    
    public void setValues(HttpServletRequest request, userPackage.User user) {
        this.request = request;
        this.user = user;
    }
    
    public int numErrors() {
        return data.numErrors();
    }
    
    public void setFormLoaded(boolean loaded) {
        this.formLoaded = loaded;
    }
    
    public boolean isFormLoaded() {
        return formLoaded;
    }
    
    public void initialiseErrorArray(int length) {
        data = new DataEntry(length);
    }

    public void setValidData(int index, boolean value) {
        data.setValidEntry(index, value);
    }
    
    /**
     * Returns the user object for this request
     * 
     * @return User
     */
    public User getUser() {
        return user;
    }
    
    public void clearErrors() {
        data.clear();
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
        return (string == null) || (string.equals(""));
    }
    
    /**
     * Add an error message to the list.
     * 
     * @param message Error message
     */
    public void addErrorMessage(int index, String message) {
        data.addErrorMessage(index, message);
    }
    
    /**
     * Returns error descriptions with the user's input
     * 
     * @return a list of the errors, separated by line breaks. 
     */
    public String getErrors(){
        return data.getErrorMessages();
    }
    
    /**
     * Checks if any errors exist
     * 
     * @return True if errors present
     */
    public boolean isValid() {
        return data.isValid();
    }
    
    /**
     * Executes the querys to the database that don't return results
     * 
     * @param insertSQL The sql query to execute
     * @return True if query was successful
     */
    public boolean insertDbQuery(String insertSQL) {
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
    
    /**
     * Executes the querys to the database that return results
     * 
     * @param selectSQL The sql query to execute
     * @return Result of the query
     */
    public ResultSet selectDbQuery(String selectSQL) {
        //create db
        Database db = Database.getSetupDatabase();
        
        ResultSet result = db.select(selectSQL);
        
        db.close();
        return result;
    }
    
}