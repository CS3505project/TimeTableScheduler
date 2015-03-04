package userDataPackage;

import toolsPackage.Database;
import toolsPackage.Validator;

/**
 * A javaBean for handling requests to add a course
 */
public final class GroupJoinCourseRequest extends UserRequest{
    private String course = "";
    private String group = "";
                            
    /**
     * Default constructor
     */
    public GroupJoinCourseRequest(){ 
        initialiseErrorArray(2);
    }
    
    /**
     * Resets the form after it is completed
     */
    private void resetForm() {
        course = "";
        group = "";
        clearErrors();
    }
    
    /**
     * Returns the course id
     * @return course id
     */
    public String getCourse() {
        return course;
    }

    /**
     * Returns the group to assign the course
     * @return group taking the course
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sets the course id
     * @param course course id
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
     * Sets the group taking the course
     * @param group Group taking this course
     */
    public void setGroup(String group) {
        if (!errorInString(group)) {
            this.group = Validator.escapeJava(group);
        }
    }
    
    /**
     * Adds the group to the course so the group is taking the course
     * @return True if success
     */
    public boolean addGroupToCourse() {
        if (isValidData(0) && !errorInString(group)) {
            boolean result = false;
            
            Database db = Database.getSetupDatabase();
            
            result = db.insert("INSERT INTO GroupTakesCourse (gid, courseid) "
                                + "VALUES (\"" + group + "\", \"" + course + "\");");
                
            resetForm();
            setFormLoaded(false);
            db.close();
            return result;
        } else {
            return false;
        }
    }
}