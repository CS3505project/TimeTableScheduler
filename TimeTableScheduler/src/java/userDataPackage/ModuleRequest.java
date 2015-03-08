package userDataPackage;


import toolsPackage.Database;
import toolsPackage.Validator;

/**
 * A javaBean for handling requests to add a module
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
    
    /**
     * Resets the form fields
     */
    private void resetForm() {
        course = "";
        moduleCode = "";
        moduleName = "";
        clearErrors();
    }
    
    /**
     * Gets the course of the module
     * @return 
     */
    public String getCourse() {
        return course;
    }
    
    /**
     * Gets the code for the module
     * @return 
     */
    public String getModuleCode() {
        return moduleCode;
    }
    
    /**
     * Gets the module name
     * @return 
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * Sets the course of the module created
     * @param course Course of the module
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
     * Sets the code for the module
     * @param moduleCode Module code
     */
    public void setModuleCode(String moduleCode) {
        if (this.errorInString(moduleCode)) {
            addErrorMessage(1, "Module code is incorrect.");
        } else {
            this.moduleCode = Validator.escapeJava(moduleCode);
            setValidData(1, true);
        }
    }
    
    /**
     * Sets the name for the module
     * @param moduleName Module name
     */
    public void setModuleName(String moduleName) {
        if (this.errorInString(moduleName)) {
            addErrorMessage(2, "Module name is incorrect.");
        } else {
            this.moduleName = Validator.escapeJava(moduleName);
            setValidData(2, true);
        }
    }

    /**
     * Creates the module for the specified course
     * @return True if the module was created successfully
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
