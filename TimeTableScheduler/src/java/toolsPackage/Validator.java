package toolsPackage;

import java.sql.ResultSet;

/**
 * Performs validation operations
 * 
 * @author John O Riordan
 */
public class Validator {
    
    /**
     * Returns an escaped version of the string entered.
     * 
     * @param input The string to escape
     * @return The escaped string
     */
    public static String escapeJava(String input){
        return StringEscapeUtils.escapeJava(input);
    }
    
    /**
     * Returns an unescaped version of the string entered.
     * 
     * @param input The string to unescape
     * @return The unescaped string
     */
    public static String unescapeJava(String input){
        return StringEscapeUtils.unescapeJava(input);
    }
    
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
        Database db = Database.getSetupDatabase();
        
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
                        if (result.getString("passwordHash").equals(passwordHash)) {
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
