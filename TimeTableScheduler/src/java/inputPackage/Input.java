/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inputPackage;

import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import toolsPackage.Database;
import toolsPackage.Hash;
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
    
    public Input(){ }
    
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
     * Create a new group in the database
     * 
     * @param groupName The groups name
     * @return True if the group was successfully added
     */
    public boolean createGroup(String groupName) {
        boolean result = false;
        
        if (!errorInString(groupName)) {
            Database db = Database.getSetupDatabase();
            
            // insert the group
            db.insert("");
            
            db.close();
        }
        
        return result;
    }
    
    /**
     * Add a list of users to the specified group
     * 
     * @param groupName The group to add the users
     * @param users List of user ids to add
     * @return True if users added successfully
     */
    public boolean joinGroup(String groupName, String[] users) {
        boolean result = false;
        
        if (!errorInString(groupName)) {
            Database db = Database.getSetupDatabase();
            
            // add the users to the group
            db.insert("");
            
            db.close();
        }
        
        return result;
    }
    
    /**
     * Inserts the new user into the database
     * Used when user signs up to the system
     * 
     * @param request The user details
     * @return True if insert can be executed
     */
    private boolean signUpUser(HttpServletRequest request) {
        Database db = Database.getSetupDatabase();
        
        String passwordHash = Hash.sha1(request.getAttribute("password"));
        
        boolean result = db.insert("");
        
        db.close();
        
        return result;
    }
    
    /**
     * Verifies the details the user entered when signing up to the website.
     * Returns a string containing an error message
     * 
     * @param request The request with all the users details
     * @param userType The type of user entering the details
     * @return Error message
     */
    public String checkSignUpDetails(HttpServletRequest request, UserType userType) {
        String error = null;
        
        if (errorInString(request.getAttribute("firstname"))) {
            error = "Please enter your first name.";
        } else if (errorInString(request.getAttribute("surname"))) {
            error = "Please enter your surname.";
        } else if (errorInString(request.getAttribute("email"))) {
            error = "Please enter your email address correctly.";
        } else if (errorInString(request.getAttribute("password"))) {
            error = "Please enter your password.";
            if (errorInString(request.getAttribute("reenter-password"))
                    && request.getAttribute("password").equals(request.getAttribute("reenter-password"))) {
                error = "Your passwords are different.";
            }
        } 
        switch (userType) {
            case ADMIN:
                if (errorInString(request.getAttribute("adminid"))) {
                    error = "Please enter your administrator ID.";
                }
                break;
            case LECTURER:
                if (errorInString(request.getAttribute("lecturerid"))) {
                    error = "Please enter your lecturer ID.";
                }
                break;
            default:
                if (errorInString(request.getAttribute("studentid"))) {
                    error = "Please enter your student ID.";
                }
                break;
        }
        
        return error;
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
        Database db = Database.getSetupDatabase();
        
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
