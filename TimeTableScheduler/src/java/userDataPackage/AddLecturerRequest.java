package userDataPackage;


import toolsPackage.Database;
import toolsPackage.Validator;

/**
 * A javaBean for handling requests to add a meeting
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
    
    private void resetForm() {
        moduleCode = "";
        lecturer = "";
        clearErrors();
    }
    
    public String getModuleCode() {
        return moduleCode;
    }
    
    public String getLecturer() {
        return lecturer;
    }
    
    /**
     * Sets the description
     * @param description description
     */
    public void setModuleCode(String moduleCode) {
        if (this.errorInString(moduleCode)) {
            addErrorMessage(0, "Module code is incorrect.");
        } else {
            this.moduleCode = Validator.escapeJava(moduleCode);
            setValidData(0, true);
        }
    }
    
    public void setLecturer(String moduleName) {
        if (this.errorInString(moduleName)) {
            addErrorMessage(1, "Module name is incorrect.");
        } else {
            this.lecturer = Validator.escapeJava(moduleName);
            setValidData(1, true);
        }
    }

    /**
     * Creates a meeting and inserts it into the database
     * 
     * @return True if the meeting was created successfully
     */
    public boolean addLecturer() {
        if (isValid()) {
            boolean result = false;
            
            Database db = Database.getSetupDatabase();
            
            result = db.insert("INSERT INTO Module (moduleCode, name, description) "
                               + "VALUES (\""+ moduleCode + "\", \"" + lecturer + "\", \"" + "" + "\");");
            
            result = db.insert("INSERT INTO ModuleInCourse (moduleCode, courseid) "
                               + "VALUES (\"" + moduleCode + "\", \"" + "\");");
                
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