/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userDataPackage;

import toolsPackage.Database;
import toolsPackage.Hash;
import toolsPackage.Validator;
import userPackage.UserType;

/**
 *
 * @author cdol1
 */
public class SignUpAdminRequest extends UserRequest {
    private int id;
    private String title = "";
    private String firstName = "";
    private String surname = "";
    private String email = "";
    
    public SignUpAdminRequest() {
        initialiseErrorArray(5);
        title = "";
        firstName = "";
        surname = "";
        email = "";
    }
    
    public void resetForm() {
        title = "";
        firstName = "";
        surname = "";
        email = "";
        clearErrors();
    }

    public void setId(String id) {
        try {
            this.id = Integer.parseInt(id);
            setValidData(0, true);
        } catch (Exception ex) {
            System.err.println("Error with duration");
            addErrorMessage(0, "Please enter your administrator ID.");
        }
    }
    
    public void setFirstName(String firstName) {
        if (errorInString(firstName)) {
            addErrorMessage(1, "Incorrect first name entered.");
            System.err.println("Error with first name.");
        } else {
            this.firstName = Validator.escapeJava(firstName);
            setValidData(1, true);
        }
    }
    
    public void setSurname(String surname) {
        if (errorInString(surname)) {
            addErrorMessage(2, "Incorrect surname entered.");
            System.err.println("Error with surname.");
        } else {
            this.surname = Validator.escapeJava(surname);
            setValidData(2, true);
        }
    }
    
    public void setEmail(String email) {
        if (Validator.isValidEmail(email)) {
            this.email = Validator.escapeJava(email);
            setValidData(3, true);
        } else {
            addErrorMessage(3, "Incorrect email entered.");
            System.err.println("Error with email.");
        }
    }
    
    public String getId() {
        return Integer.toString(id);
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
 
    private boolean validPasswords() {
        boolean result = true;
        
        String firstPassword = (String)getRequest().getParameter("password");
        String secondPassword = (String)getRequest().getParameter("rePassword");
        
        if (errorInString(firstPassword)) {
            addErrorMessage(4, "Enter a password");
            result = false;
        } else if (errorInString(secondPassword)) {
            addErrorMessage(4, "Please re-enter your password");
            result = false;
        } else if (!firstPassword.equals(secondPassword)) {
            addErrorMessage(4, "Passwords are different.");
            result = false;
        } else {
            setValidData(4, result);
        }
        return result;
    }
    
    /**
     * Inserts the new user into the database
     * Used when user signs up to the system
     * 
     * @return True if insert can be executed
     */
    public boolean signUpAdmin() {System.out.println("here: 14");
        boolean result = false;
        if (validPasswords() && isValid()) {
            Database db = Database.getSetupDatabase();
            String passwordHash = Hash.sha1((String)getRequest().getParameter("password"));
            
            result = db.insert("INSERT INTO User (passwordHash, email, firstName, surname) "
                               + "VALUES (\"" + passwordHash + "\", \"" + email + "\", \"" + firstName + "\", \"" + surname + "\");");
            
            if (result) {
                int uid = db.getPreviousAutoIncrementID("User");
                result = db.insert("INSERT INTO Admin (uid, adminid) "
                                   + "VALUES (" + uid + ", " + id + ");");
            }
            
            if (!result) {
                System.err.println("Problem creating user in database.");
            } else {
                setFormLoaded(false);
                resetForm();
            }
            db.close();
        }        
        return result;
    }
}