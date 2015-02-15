/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inputPackage;

import java.sql.ResultSet;
import toolsPackage.Database;
import toolsPackage.Validator;
import userPackage.Admin;
import userPackage.Lecturer;
import userPackage.Student;
import userPackage.User;

/**
 *
 * @author Pc
 */
public class Input {
    
    public Input(){
        
    }
    
    public boolean login(String email, String password) {
        boolean result = false;
        
        if (email != null && password != null
                && !email.equals("") && !password.equals("")) {
            result = Validator.isValidLogin(email, password);
        }
        
        return result;
    }
    
    public static User getUserDetails(String email) {
        Database db = new Database();
        // for local use only outside college network with putty
        db.setup("127.0.0.1:3310", "2016_kmon1", "kmon1", "augeheid");
       
        //for use in college network
        //db.setup("cs1.ucc.ie:3306", "2016_kmon1", "kmon1", "augeheid");
        
        User user = null;
        
        try {
            // check if the user ID belongs to a student
            if (user == null) {
                ResultSet result = db.select("");

                while (result.next()) {
                    user = new Student(result.getString(""),
                                       result.getString(""),
                                       result.getString(""),
                                       result.getString(""),
                                       result.getString(""));
                }
                
                // if not then it could be a lecturer's user ID
                if (user == null) {
                    result = db.select("");

                    while (result.next()) {
                        user = new Lecturer(result.getString(""),
                                           result.getString(""),
                                           result.getString(""),
                                           result.getString(""),
                                           result.getString(""),
                                           result.getString(""));
                    }
                    
                    // then must be an admin user or an invalid user ID
                    if (user == null) {
                        result = db.select("");

                        while (result.next()) {
                            user = new Admin(result.getString(""),
                                             result.getString(""),
                                             result.getString(""),
                                             result.getString(""),
                                             result.getString(""));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            
        }

        return user;
    }
}