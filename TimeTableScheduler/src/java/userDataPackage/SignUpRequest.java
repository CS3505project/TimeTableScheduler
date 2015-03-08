package userDataPackage;

import toolsPackage.Database;
import toolsPackage.Hash;
import toolsPackage.Validator;
import userPackage.UserType;
import static userPackage.UserType.LECTURER;

/**
 * Handles a request to sign up to the system
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
    
    /**
     * Resets the form fields
     */
    public void resetForm() {
        id = "";
        title = "";
        firstName = "";
        surname = "";
        email = "";
        clearErrors();
    }
    
    /**
     * Sets the typs of user signing in
     * @param userType 
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    
    /**
     * Gets the user type
     * @return User type
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Sets the id of the user
     * @param id user id
     */
    public void setId(String id) {
        switch (userType) {
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
    
    /**
     * Sets the title of the lecturer
     * Only valid for lecturers
     * @param title Lecturer title e.g. Dr.
     */
    public void setTitle(String title) {
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
    
    /**
     * Sets the users first name
     * @param firstName 
     */
    public void setFirstName(String firstName) {
        if (errorInString(firstName)) {
            addErrorMessage(2, "Incorrect first name entered.");
            System.err.println("Error with first name.");
        } else {
            this.firstName = Validator.escapeJava(firstName);
            setValidData(2, true);
        }
    }
    
    /**
     * Sets the users surname
     * @param surname 
     */
    public void setSurname(String surname) {
        if (errorInString(surname)) {
            addErrorMessage(3, "Incorrect surname entered.");
            System.err.println("Error with surname.");
        } else {
            this.surname = Validator.escapeJava(surname);
            setValidData(3, true);
        }
    }
    
    /**
     * Sets the email address for the user
     * @param email 
     */
    public void setEmail(String email) {
        if (Validator.isValidEmail(email)) {
            this.email = Validator.escapeJava(email);
            setValidData(4, true);
        } else {
            addErrorMessage(4, "Incorrect email entered.");
            System.err.println("Error with email.");
        }
    }
    
    /**
     * Gets the id of the user
     * @return 
     */
    public String getId() {
        return Validator.unescapeJava(id);
    }
    
    /**
     * Gets the title of the lecturer
     * @return 
     */
    public String getTitle() {
        return Validator.unescapeJava(title);
    }
    
    /**
     * Gets the users first name
     * @return 
     */
    public String getFirstName() {
        return Validator.unescapeJava(firstName);
    }
    
    /**
     * Gets the users surname
     * @return 
     */
    public String getSurname() {
        return Validator.unescapeJava(surname);
    }
    
    /**
     * Gets the email address of the user
     * @return 
     */
    public String getEmail() {
        return Validator.unescapeJava(email);
    }
 
    /**
     * Validates the passwords entered by the user on the form
     * @return true if valid
     */
    private boolean validPasswords() {
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
    public boolean signUp() {
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
                }
            }
            
            if (!result) {
                System.err.println("Problem creating user in database.");
            } else {
                resetForm();
                setFormLoaded(false);
            }
            db.close();
        }        
        return result;
    }
}