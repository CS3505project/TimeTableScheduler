package userDataPackage;

import timeTablePackage.EventPriority;
import toolsPackage.Database;
import toolsPackage.Validator;

/**
 * A javaBean for handling requests to add a meeting
 */
public final class CourseRequest extends UserRequest{
    private String course = "";
    private String name = "";
    private String department = "";
    private int year = 1;
    private String group = "";
        
    private boolean setup = false;
                
    /**
     * Default constructor
     */
    public CourseRequest(){ 
        initialiseErrorArray(4);
    }
    
    private void resetForm() {
        course = "";
        name = "";
        department = "";
        year = 1;
        clearErrors();
    }
    
    public String getCourse() {
        return course;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public String getYear() {
        return Integer.toString(year);
    }
    
    public String getGroup() {
        return group;
    }

    /**
     * sets the venue in which the meeting is to take place.
     * @param venue the venue from the form
     */
    public void setCourse(String course){
        if (this.errorInString(course)) {
            this.addErrorMessage(0, "Course code is incorrect.");
        } else {
            this.course = Validator.escapeJava(course);
            setValidData(0, true);
        }
    }
    
    /**
     * Sets the description
     * @param description description
     */
    public void setName(String name) {
        if (this.errorInString(name)) {
            addErrorMessage(1, "Course name is incorrect.");
        } else {
            this.name = Validator.escapeJava(name);
            setValidData(1, true);
        }
    }
    
    public void setDepartment(String department) {
        if (this.errorInString(department)) {
            addErrorMessage(2, "Department is incorrect.");
        } else {
            this.department = Validator.escapeJava(department);
            setValidData(2, true);
        }
    }
    
    public void setYear(String year) {
        try {
            this.year = Integer.parseInt(year);
            setValidData(3, true);
        } catch (Exception ex) {
            addErrorMessage(3, "Year is incorrect.");
        }
    }
    
    public void setGroup(String group) {
        if (!errorInString(group)) {
            this.group = Validator.escapeJava(group);
        }
    }

    /**
     * Creates a meeting and inserts it into the database
     * 
     * @return True if the meeting was created successfully
     */
    public boolean createCourse() {
        if (isValid()) {
            boolean result = false;
            
            Database db = Database.getSetupDatabase();
            
            result = db.insert("INSERT INTO Course (courseCode, name, department, year) "
                                + "VALUES (\"" + course + "\", \"" + name + "\", \"" 
                                + department + "\", " + year + ");");
                
            resetForm();
            setup = false;
            db.close();
            return result;
        } else {
            return false;
        }
    }
    
    public boolean addGroupToCourse() {
        if (isValidData(0) && !errorInString(group)) {
            boolean result = false;
            
            Database db = Database.getSetupDatabase();
            
            result = db.insert("INSERT INTO GroupTakesCourse (gid, courseid) "
                                + "VALUES (\"" + group + "\", \"" + course + "\");");
                
            resetForm();
            setup = false;
            db.close();
            return result;
        } else {
            return false;
        }
    }
}
