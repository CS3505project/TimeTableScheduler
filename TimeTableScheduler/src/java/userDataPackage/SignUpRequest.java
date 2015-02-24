/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userDataPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import toolsPackage.Database;
import toolsPackage.Hash;
import toolsPackage.StringEscapeUtils;
import toolsPackage.Validator;
import userPackage.Admin;
import userPackage.Lecturer;
import userPackage.Student;
import userPackage.User;
import userPackage.UserType;
import static userPackage.UserType.ADMIN;
import static userPackage.UserType.LECTURER;

/**
 *
 * @author cdol1
 */
public class SignUpRequest extends UserRequest {
    private String id;
    private String title;
    private String firstName;
    private String surname;
    private String email;
    private UserType userType;
    private boolean signedUp;
    
    public SignUpRequest() { }
    
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    
    public UserType getUserType() {
        return userType;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setFirstName(String firstName) {
        if (!errorInString(firstName)) {
            this.firstName = Validator.escapeJava(firstName);
        } else {
            addError("Incorrect first name entered.");
            System.err.println("Error with first name.");
        }
    }
    
    public void setSurname(String surname) {
        if (!errorInString(surname)) {
            this.surname = Validator.escapeJava(surname);
        } else {
            addError("Incorrect surname entered.");
            System.err.println("Error with surname.");
        }
    }
    
    public void setEmail(String email) {
        if (Validator.isValidEmail(email)) {
            this.email = Validator.escapeJava(email);
        } else {
            addError("Incorrect email entered.");
            System.err.println("Error with email.");
        }
    }
    
    private void setUserID(String id) {
        switch (userType) {
            case ADMIN:
                if (errorInString(id)) {
                    addError("Please enter your administrator ID.");
                } else {
                    this.id = Validator.escapeJava(id);
                }
                break;
            case LECTURER:
                if (errorInString(id)) {
                    addError("Please enter your lecturer ID.");
                } else {
                    this.id = Validator.escapeJava(id);
                }
                break;
            default:
                if (errorInString(id)) {
                    addError("Please enter your student ID.");
                } else {
                    this.id = Validator.escapeJava(id);
                }
                break;
        }
    }
    
    public String getTitle() {
        return Validator.unescapeJava(title);
    }
    
    public String getFirstName() {
        return Validator.unescapeJava(firstName);
    }
    
    public String getSurname() {
        return Validator.unescapeJava(surname);
    }
    
    public String getEmail() {
        return Validator.unescapeJava(email);
    }
    
    public String getUserID() {
        return Validator.unescapeJava(id);
    }
    
    private boolean validPassword() {
        boolean valid = true;
        if (errorInString((String)getRequest().getAttribute("password"))) {
            valid = false;
            addError("Please enter your password.");
            if (errorInString((String)getRequest().getAttribute("rePassword"))
                    && !((String)getRequest().getAttribute("password")).equals(((String)getRequest().getAttribute("rePassword")))) {
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
    public boolean signUp(String userType) {        
        boolean result = false;
        if (validPassword() && this.isValid()) {
            Database db = Database.getSetupDatabase();

            String passwordHash = Hash.sha1((String)getRequest().getAttribute("password"));

            result = db.insert("");

            db.close();
        } else {
            addError("Problem creating user.");
            System.err.println("Problem creating user in database.");
        }
        
        signedUp = result;
        
        return result;
    }

    public User getUserObject() {
        User user = null;
        if (signedUp) {
            Database db = Database.getSetupDatabase();
            ResultSet result = db.select("");
            String uid = "";
            try {
                while (result.next()) {
                    uid = result.getString("uid");
                }
            } catch (SQLException ex) {
                System.err.println("Error get user id from database.");
            }

            
            switch (userType) {
                case ADMIN:
                    user = new Admin(id, email, firstName, surname, uid);
                    break;
                case LECTURER:
                    user = new Lecturer(id, email, title, firstName, surname, uid);
                    break;
                default:
                    user = new Student(id, email, firstName, surname, uid);
                    break;
            }
        }
        return user;
    }
}