package userDataPackage;


import toolsPackage.Database;
import toolsPackage.Validator;

/**
 * A javaBean for handling requests to add a meeting
 */
public final class ModuleRequest extends UserRequest{
    private String course = "";
    private String moduleCode = "";
    private String moduleName = "";
        
    private boolean setup = false;
    
    public static final String TIME_FORMAT = "HH:mm:ss";
            
    /**
     * Default constructor
     */
    public ModuleRequest(){ 
        initialiseErrorArray(3);
        course = "";
        moduleCode = "";
        moduleName = "";
    }
    
    private void resetForm() {
        course = "";
        moduleCode = "";
        moduleName = "";
        clearErrors();
    }
    
    public String getCourse() {
        return course;
    }
    
    public String getModuleCode() {
        return moduleCode;
    }
    
    public String getModuleName() {
        return moduleName;
    }

    /**
     * sets the venue in which the meeting is to take place.
     * @param venue the venue from the form
     */
    public void setCourse(String course){
        if (this.errorInString(course)) {
            this.addErrorMessage(0, "Course is incorrect.");
        } else {
            this.course = Validator.escapeJava(course);
            setValidData(0, true);
        }
    }
    
    /**
     * Sets the description
     * @param description description
     */
    public void setModuleCode(String moduleCode) {
        if (this.errorInString(moduleCode)) {
            addErrorMessage(1, "Module code is incorrect.");
        } else {
            this.moduleCode = Validator.escapeJava(moduleCode);
            setValidData(1, true);
        }
    }
    
    public void setModuleName(String moduleName) {
        if (this.errorInString(moduleName)) {
            addErrorMessage(2, "Module name is incorrect.");
        } else {
            this.moduleName = Validator.escapeJava(moduleName);
            setValidData(2, true);
        }
    }

    /**
     * Creates a meeting and inserts it into the database
     * 
     * @return True if the meeting was created successfully
     */
    public boolean createModule() {
        if (isValid()) {
            boolean result = false;
            
            Database db = Database.getSetupDatabase();
            
            result = db.insert("INSERT INTO Module (moduleCode, name, description) "
                               + "VALUES (\""+ moduleCode + "\", \"" + moduleName + "\", \"" + "" + "\");");
            
            result = db.insert("INSERT INTO ModuleInCourse (moduleCode, courseid) "
                               + "VALUES (\"" + moduleCode + "\", \"" + course + "\");");
                
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
