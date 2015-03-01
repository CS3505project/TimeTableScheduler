/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userDataPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import toolsPackage.Database;
import toolsPackage.Hash;
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
    private String id = "";
    private String title = "";
    private String firstName = "";
    private String surname = "";
    private String email = "";
    private UserType userType;
    
    public SignUpRequest() {
        initialiseErrorArray(6);
        id = "";
        title = "";
        firstName = "";
        surname = "";
        email = "";
    }
    
    public void resetForm() {
        id = "";
        title = "";
        firstName = "";
        surname = "";
        email = "";
        clearErrors();
    }
    
    public void setUserType(UserType userType) { System.out.println("here: 1" + userType);
        this.userType = userType;
    }
    
    public UserType getUserType() {System.out.println("here: 2");
        return userType;
    }

    public void setId(String id) {System.out.println("here: 3");
        switch (userType) {
            case ADMIN:
                if (errorInString(id)) {
                    addErrorMessage(0, "Please enter your administrator ID.");
                } else {
                    this.id = Validator.escapeJava(id);
                    setValidData(0, true);
                }
                break;
            case LECTURER:
                if (errorInString(id)) {
                    addErrorMessage(0, "Please enter your lecturer ID.");
                } else {
                    this.id = Validator.escapeJava(id);
                    setValidData(0, true);
                }
                break;
            default:
                if (errorInString(id)) {
                    addErrorMessage(0, "Please enter your student ID.");
                } else {
                    this.id = Validator.escapeJava(id);
                    setValidData(0, true);
                }
                break;
        }
    }
    
    public void setTitle(String title) {System.out.println("here: 4");
        if (userType.equals(UserType.LECTURER)) {
            if (errorInString(title)) {
                addErrorMessage(1, "Incorrect title entered.");
                System.err.println("Error with title.");
            } else {
                this.title = Validator.escapeJava(title);
                setValidData(1, true);
            }
        } else {
            setValidData(1, true);
        }
    }
    
    public void setFirstName(String firstName) {System.out.println("here: 5");
        if (errorInString(firstName)) {
            addErrorMessage(2, "Incorrect first name entered.");
            System.err.println("Error with first name.");
        } else {
            this.firstName = Validator.escapeJava(firstName);
            setValidData(2, true);
        }
    }
    
    public void setSurname(String surname) {System.out.println("here: 6");
        if (errorInString(surname)) {
            addErrorMessage(3, "Incorrect surname entered.");
            System.err.println("Error with surname.");
        } else {
            this.surname = Validator.escapeJava(surname);
            setValidData(3, true);
        }
    }
    
    public void setEmail(String email) {System.out.println("here: 7");
        if (Validator.isValidEmail(email)) {
            this.email = Validator.escapeJava(email);
            setValidData(4, true);
        } else {
            addErrorMessage(4, "Incorrect email entered.");
            System.err.println("Error with email.");
        }
    }
    
    public String getId() {System.out.println("here: 8");
        return Validator.unescapeJava(id);
    }
    
    public String getTitle() {System.out.println("here: 9");
        return Validator.unescapeJava(title);
    }
    
    public String getFirstName() {System.out.println("here: 410");
        return Validator.unescapeJava(firstName);
    }
    
    public String getSurname() {System.out.println("here: 11");
        return Validator.unescapeJava(surname);
    }
    
    public String getEmail() {System.out.println("here: 12");
        return Validator.unescapeJava(email);
    }
 
    private boolean validPasswords() {System.out.println("here: 13");
        boolean result = true;
        
        String firstPassword = (String)getRequest().getParameter("password");
        String secondPassword = (String)getRequest().getParameter("rePassword");
        
        if (errorInString(firstPassword)) {
            addErrorMessage(5, "Enter a password");
            result = false;
        } else if (errorInString(secondPassword)) {
            addErrorMessage(5, "Please re-enter your password");
            result = false;
        } else if (!firstPassword.equals(secondPassword)) {
            addErrorMessage(5, "Passwords are different.");
            result = false;
        } else {
            setValidData(5, result);
        }
        return result;
    }
    
    /**
     * Inserts the new user into the database
     * Used when user signs up to the system
     * 
     * @return True if insert can be executed
     */
    public boolean signUp() {System.out.println("here: 14");
        boolean result = false;
        if (validPasswords() && isValid()) {
            Database db = Database.getSetupDatabase();
            String passwordHash = Hash.sha1((String)getRequest().getParameter("password"));
            
            result = db.insert("INSERT INTO User (passwordHash, email, firstName, surname) "
                               + "VALUES (\"" + passwordHash + "\", \"" + email + "\", \"" + firstName + "\", \"" + surname + "\");");
            
            if (result) {
                int uid = db.getPreviousAutoIncrementID("User");
                switch (userType) {
                    case STUDENT:
                        result = db.insert("INSERT INTO Student (uid, studentid) "
                                   + "VALUES (\"" + uid + "\", \"" + id + "\");");
                        break;
                    case LECTURER:
                        result = db.insert("INSERT INTO Lecturer (uid, lecturerid, title) "
                                   + "VALUES (\"" + uid + "\", \"" + id + "\", \"" + title + "\");");
                        break;
                    case ADMIN:
                        result = db.insert("INSERT INTO Admin (uid, adminid) "
                                   + "VALUES (\"" + uid + "\", \"" + id + "\");");
                        break;
                }
            }
            
            if (!result) {
                System.err.println("Problem creating user in database.");
            } else {
                resetForm();
            }
            db.close();
        }        
        return result;
    }
}