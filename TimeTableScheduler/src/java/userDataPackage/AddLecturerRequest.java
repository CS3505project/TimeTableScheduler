package userDataPackage;

import toolsPackage.Database;
import toolsPackage.Validator;

/**
 * A javaBean for handling requests to add a lecturer to a module
 */
public final class AddLecturerRequest extends UserRequest{
    private String moduleCode = "";
    private String lecturer = "";
        
    private boolean setup = false;
    
    public static final String TIME_FORMAT = "HH:mm:ss";
            
    /**
     * Default constructor
     */
    public AddLecturerRequest(){ 
        initialiseErrorArray(2);
        moduleCode = "";
        lecturer = "";
    }
    
    /**
     * Reset the form fields
     */
    private void resetForm() {
        moduleCode = "";
        lecturer = "";
        clearErrors();
    }
    
    /**
     * Gets the module to add lecturer
     * @return Module code
     */
    public String getModuleCode() {
        return moduleCode;
    }
    
    /**
     * Gets the lecturer being added
     * @return Lecturer id
     */
    public String getLecturer() {
        return lecturer;
    }
    
    /**
     * Sets the module code
     * @param moduleCode module code
     */
    public void setModuleCode(String moduleCode) {
        if (this.errorInString(moduleCode)) {
            addErrorMessage(0, "Module code is incorrect.");
        } else {
            this.moduleCode = Validator.escapeJava(moduleCode);
            setValidData(0, true);
        }
    }
    
    /**
     * Sets the lecturer id
     * @param lecturerId lecturer id
     */
    public void setLecturer(String lecturerId) {
        if (this.errorInString(lecturerId)) {
            addErrorMessage(1, "Module name is incorrect.");
        } else {
            this.lecturer = Validator.escapeJava(lecturerId);
            setValidData(1, true);
        }
    }

    /**
     * Adds a lecturer to a module so the lecturer can teach that module
     * 
     * @return True if the lecturer added successfully
     */
    public boolean addLecturer() {
        if (isValid()) {
            boolean result = false;
            
            Database db = Database.getSetupDatabase();
            
            result = db.insert("INSERT INTO TeachesModule (uid, moduleCode) "
                               + "VALUES (\""+ lecturer + "\", \"" + moduleCode + "\");");
                
            resetForm();
            setFormLoaded(false);
            setup = false;
            db.close();
            return result;
        } else {
            return false;
        }
    }
}