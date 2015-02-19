/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inputPackage;

import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import toolsPackage.Database;
import toolsPackage.Validator;
import userPackage.Admin;
import userPackage.Lecturer;
import userPackage.Student;
import userPackage.User;
import userPackage.UserType;

/**
 * Handles input from the UI to the system
 * 
 * @author John O Riordan
 */
public class Input {
    
    public Input(){
        
    }
    
    public String checkSignUpDetails(HttpServletRequest request, UserType userType) {
        String error = "";
        if (request.getAttribute("studentid") == null) {
            error = "Please enter your Student ID.";
        }

        return error;
    }
    
    /**
     * Checks the login details provided by the user
     * 
     * @param email user email
     * @param password user password
     * @return True if login details correct
     */
    public boolean login(String email, String password) {
        boolean result = false;
        
        if (email != null && password != null
                && !email.equals("") && !password.equals("")) {
            result = Validator.isValidLogin(email, password);
        }
        
        return result;
    }
    
    /**
     * Returns the User object that corresponds to the email address
     * 
     * @param email user email
     * @return User object
     */
    public static User getUserDetails(String email) {
        Database db = new Database();
        // for local use only outside college network with putty
        //db.setup("127.0.0.1:3310", "2016_kmon1", "kmon1", "augeheid");
       
        //for use in college network
        db.setup("cs1.ucc.ie:3306", "2016_kmon1", "kmon1", "augeheid");
        
        User user = null;
        
        try {
            // check if the user ID belongs to a student
            if (user == null) {
                ResultSet result = db.select("SELECT * " +
                                            "FROM Student JOIN User " +
                                            "ON uid = userid " +
                                            "WHERE email = \"" + email + "\";");

                while (result.next()) {
                    user = new Student(result.getString("studentid"),
                                       result.getString("email"),
                                       result.getString("firstname"),
                                       result.getString("surname"),
                                       result.getString("uid"));
                }
                
                // if not then it could be a lecturer's user ID
                if (user == null) {
                    result = db.select("SELECT * " +
                                        "FROM Lecturer JOIN User " +
                                        "ON uid = userid " +
                                        "WHERE email = \"" + email + "\";");

                    while (result.next()) {
                        user = new Lecturer(result.getString("lecturerid"),
                                           result.getString("email"),
                                           result.getString("title"),
                                           result.getString("firstname"),
                                           result.getString("surname"),
                                           result.getString("uid"));
                    }
                    
                    // then must be an admin user or an invalid user ID
                    if (user == null) {
                        result = db.select("SELECT * " + 
                                            "FROM Admin JOIN User " +
                                            "ON uid = userid " +
                                            "WHERE email = \"" + email + "\";");

                        while (result.next()) {
                            user = new Admin(result.getString("adminid"),
                                             result.getString("email"),
                                             result.getString("firstname"),
                                             result.getString("surname"),
                                             result.getString("uid"));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            
        }
        db.close();

        return user;
    }
}
