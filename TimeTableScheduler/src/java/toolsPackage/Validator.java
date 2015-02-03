package toolsPackage;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * 
 * 
 * @author John O Riordan
 */
public class Validator {
    
    
    
    
    public static boolean checkLogin(String username, String password)
    {
        boolean success = false;
        Database db = new Database();
        db.setupFromPropertiesFile("database.properties");
        
        ResultSet result = db.select("SELECT uid FROM students WHERE studentid = " + username + ";");
        if (db.getNumRows(result) < 1) {
            result = db.select("SELECT uid FROM lecturer WHERE lecturerid = " + username + ";");
            if (db.getNumRows(result) < 1) {
                result = db.select("SELECT uid FROM admin WHERE adminid = " + username + ";");
            }
        }
        
        if (db.getNumRows(result) > 0) {
            String passwordHash = Hash.sha1(password);
            try {
                result = db.select("SELECT passwordHash FROM user WHERE userid = " + result.getString("uid") + ";");
                if (result.getString("passwordHash").equals(passwordHash)) {
                   success = true; 
                }
            } catch (Exception e) {
                
            }
        }
        
        db.close();
        return success;
    }
}
