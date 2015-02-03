package toolsPackage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

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
     * @return 
     */
    public static boolean isValidLogin(String email, String password)
    {
        boolean success = false;
        Database db = new Database();
        // connect to the database using the properties file
        db.setupFromPropertiesFile("database.properties");
        
        // use the emial validator to check the email
        EmailValidator emailValidator =  EmailValidator.getInstance();
        if (emailValidator.isValid(password)) { 
            // hash the password provided by the user to check against 
            // entry in the database
            String passwordHash = Hash.sha1(password);
            try {
                // retrieve the hashed password from the database
                ResultSet result = db.select("SELECT passwordHash FROM user WHERE email = " + email + ";");
                // compare the two hashed passwords
                if (db.getNumRows(result) > 0 && result.getString("passwordHash").equals(passwordHash)) {
                    // successful login
                    success = true; 
                }
            } catch (Exception e) {

            }
        }
        
        // close database to save resources
        db.close();
        return success;
    }
}
