package userDataPackage;

import timeTablePackage.EventPriority;
import toolsPackage.Database;
import toolsPackage.Validator;

/**
 * A javaBean for handling requests to add a meeting
 */
public final class CourseRequest extends UserRequest{
    private String group = "";
    private String moduleCode = "";
    private String moduleName = "";
        
    private boolean setup = false;
    
    public static final String TIME_FORMAT = "HH:mm:ss";
            
    /**
     * Default constructor
     */
    public CourseRequest(){ 
        initialiseErrorArray(3);
        group = "";
        moduleCode = "";
        moduleName = "";
    }
    
    private void resetForm() {
        group = "";
        moduleCode = "";
        moduleName = "";
        clearErrors();
    }
    
    public String getGroup() {
        return group;
    }
    
    public String getModuleCode() {
        return moduleCode;
    }
    
    public String getModuleName() {
        return moduleCode;
    }

    /**
     * sets the venue in which the meeting is to take place.
     * @param venue the venue from the form
     */
    public void setGroup(String group){
        if (this.errorInString(group)) {
            this.addErrorMessage(0, "Group is incorrect.");
        } else {
            this.group = Validator.escapeJava(group);
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
            
            result = db.insert("INSERT INTO Module (date, time, room, description, priority, organiser_uid) "
                                            + "VALUES (\""+ meetingDate + "\", \"" + timeFormat.format(cal.getTime()) + "\", \"" + venue + "\", \"" 
                                            + description + "\", " + EventPriority.MEETING.getPriority() + ", " + getUser().getUserID() + ");");
                
            resetForm();
            setup = false;
            db.close();
            return result;
        } else {
            return false;
        }
    }
}
