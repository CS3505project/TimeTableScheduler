/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userDataPackage;

import javax.servlet.http.HttpServletRequest;
import toolsPackage.Database;
import toolsPackage.Hash;
import toolsPackage.Validator;
import userPackage.UserType;
import static userPackage.UserType.ADMIN;
import static userPackage.UserType.LECTURER;

/**
 *
 * @author cdol1
 */
public class SignUpRequest extends userRequest {
    private String id;
    private String firstName;
    private String surname;
    private String email;
    
    public SignUpRequest(HttpServletRequest request, userPackage.User user){
        super(request, user);
    }
    
    public void setID(String id) {
        if (!errorInString(id)) {
            this.id = id;
        } else {
            addError("Incorrect id entered.");
            System.err.println("Error with id.");
        }
    }
    
    public void setFirstName(String firstName) {
        if (!errorInString(firstName)) {
            this.firstName = firstName;
        } else {
            addError("Incorrect first name entered.");
            System.err.println("Error with first name.");
        }
    }
    
    public void setSurname(String surname) {
        if (!errorInString(surname)) {
            this.surname = surname;
        } else {
            addError("Incorrect surname entered.");
            System.err.println("Error with surname.");
        }
    }
    
    public void setEmail(String email) {
        if (Validator.isValidEmail(email)) {
            this.email = email;
        } else {
            addError("Incorrect email entered.");
            System.err.println("Error with email.");
        }
    }
    
    public String getID() {
        return id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public String getEmail() {
        return email;
    }
    
    private boolean validPassword() {
        boolean valid = true;
        if (errorInString((String)getRequest().getAttribute("password"))) {
            valid = false;
            addError("Please enter your password.");
            if (errorInString((String)getRequest().getAttribute("reenter-password"))
                    && !getRequest().getAttribute("password").equals(getRequest().getAttribute("reenter-password"))) {
                valid = false;
                addError("Your passwords are different.");
            }
        } 
        return valid;
    }
    
    /**
     * Inserts the new user into the database
     * Used when user signs up to the system
     * 
     * @param request The user details
     * @return True if insert can be executed
     */
    private boolean signUp(String userType) {
        getUserID(userType);
        
        boolean result = false;
        if (validPassword()) {
            Database db = Database.getSetupDatabase();

            String passwordHash = Hash.sha1((String)getRequest().getAttribute("password"));

            result = db.insert("");

            db.close();
        } else {
            addError("Problem creating user.");
            System.err.println("Problem creating user in database.");
        }
        
        return result;
    }
    
    private void getUserID(String userType) {
        switch (UserType.getUserType(userType)) {
            case ADMIN:
                if (errorInString(id = (String)getRequest().getAttribute("adminid"))) {
                    addError("Please enter your administrator ID.");
                }
                break;
            case LECTURER:
                if (errorInString(id = (String)getRequest().getAttribute("lecturerid"))) {
                    addError("Please enter your lecturer ID.");
                }
                break;
            default:
                if (errorInString(id = (String)getRequest().getAttribute("studentid"))) {
                    addError("Please enter your student ID.");
                }
                break;
        }
    }
    
}