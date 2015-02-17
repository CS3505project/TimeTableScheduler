package toolsPackage;

import java.sql.ResultSet;

/**
 * Performs validation operations
 * 
 * @author John O Riordan
 */
public class Validator {
    
    /**
     * Checks if the email address is a valid email
     * 
     * @param email The email address
     * @return True if valid email
     */
    public static boolean isValidEmail(String email) {
        // use the email validator to validate the emial
        return EmailValidator.getInstance().isValid(email);
    }
    
    /**
     * Check if the login credentials are valid
     * 
     * @param email The user email address
     * @param password The user password
     * @return True if login successful
     */
    public static boolean isValidLogin(String email, String password)
    {
        boolean success = false;
        Database db = new Database();
         // for local use only outside college network with putty
        //db.setup("127.0.0.1:3310", "2016_kmon1", "kmon1", "augeheid");
       
        //for use in college network
        db.setup("cs1.ucc.ie:3306", "2016_kmon1", "kmon1", "augeheid");
        
        // use the emial validator to check the email
        EmailValidator emailValidator =  EmailValidator.getInstance();
        if (emailValidator.isValid(email)) { 
            // hash the password provided by the user to check against 
            // entry in the database
            
            String passwordHash = Hash.sha1(password);
            try {
                // retrieve the hashed password from the database
                ResultSet result = db.select("SELECT passwordHash FROM User WHERE email = \"" + email + "\";");
                
                if (db.getNumRows(result) == 1) {                
                    while(result.next()) {
                        // compare the two hashed passwords
                        if (result.getString("passwordHash").equals(password)) {
                            // successful login
                            success = true; 
                        }
                    }
                }
            } catch (Exception e) {

            }
        }
        
        // close database to save resources
        db.close();
        return success;
    }
}
