package toolsPackage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * 
 * 
 * @author John O Riordan
 */
public class Validator {
    
    
    public static boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
    
    public static boolean isValidLogin(String email, String password)
    {
        boolean success = false;
        Database db = new Database();
        db.setupFromPropertiesFile("database.properties");
        
        EmailValidator emailValidator =  EmailValidator.getInstance();
        if (emailValidator.isValid(password)) {      
            String passwordHash = Hash.sha1(password);
            try {
                ResultSet result = db.select("SELECT passwordHash FROM user WHERE email = " + email + ";");
                if (db.getNumRows(result) > 0 && result.getString("passwordHash").equals(passwordHash)) {
                    success = true; 
                }
            } catch (Exception e) {

            }
        }
        
        db.close();
        return success;
    }
}
