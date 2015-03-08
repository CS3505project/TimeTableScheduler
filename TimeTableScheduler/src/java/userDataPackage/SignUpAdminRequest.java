package userDataPackage;

import toolsPackage.Database;
import toolsPackage.Hash;
import toolsPackage.Validator;

/**
 * Handles a request to create a new administrator
 * @author cdol1
 */
public class SignUpAdminRequest extends UserRequest {
    private int id;
    private String firstName = "";
    private String surname = "";
    private String email = "";
    
    public SignUpAdminRequest() {
        initialiseErrorArray(5);
        firstName = "";
        surname = "";
        email = "";
    }
    
    /**
     * Resets the form fields
     */
    public void resetForm() {
        firstName = "";
        surname = "";
        email = "";
        clearErrors();
    }

    /**
     * Sets the admins id
     * @param id 
     */
    public void setId(String id) {
        try {
            this.id = Integer.parseInt(id);
            setValidData(0, true);
        } catch (Exception ex) {
            System.err.println("Error with duration");
            addErrorMessage(0, "Please enter your administrator ID.");
        }
    }
    
    /**
     * Sets the admins first name
     * @param firstName 
     */
    public void setFirstName(String firstName) {
        if (errorInString(firstName)) {
            addErrorMessage(1, "Incorrect first name entered.");
            System.err.println("Error with first name.");
        } else {
            this.firstName = Validator.escapeJava(firstName);
            setValidData(1, true);
        }
    }
    
    /**
     * Sets the admins surname
     * @param surname 
     */
    public void setSurname(String surname) {
        if (errorInString(surname)) {
            addErrorMessage(2, "Incorrect surname entered.");
            System.err.println("Error with surname.");
        } else {
            this.surname = Validator.escapeJava(surname);
            setValidData(2, true);
        }
    }
    
    /**
     * Sets the admins email address
     * @param email 
     */
    public void setEmail(String email) {
        if (Validator.isValidEmail(email)) {
            this.email = Validator.escapeJava(email);
            setValidData(3, true);
        } else {
            addErrorMessage(3, "Incorrect email entered.");
            System.err.println("Error with email.");
        }
    }
    
    /**
     * Gets the id for the admin
     * @return 
     */
    public String getId() {
        return Integer.toString(id);
    }
    
    /**
     * Gets the admin first name
     * @return 
     */
    public String getFirstName() {
        return Validator.unescapeJava(firstName);
    }
    
    /**
     * Gets the admins surname
     * @return 
     */
    public String getSurname() {
        return Validator.unescapeJava(surname);
    }
    
    /**
     * Gets the admins email address
     * @return 
     */
    public String getEmail() {
        return Validator.unescapeJava(email);
    }
 
    /**
     * Validates the admins passwords entered on the form
     * @return true if valid
     */
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
     * Inserts the new admin into the database
     * Used when admin is created by another admin
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