package userDataPackage;

import toolsPackage.Database;
import toolsPackage.Validator;

/**
 * A javaBean for handling requests to add a course
 */
public final class CourseRequest extends UserRequest{
    private String course = "";
    private String name = "";
    private String department = "";
    private int year = 1;
    private String group = "";
                            
    /**
     * Default constructor
     */
    public CourseRequest(){ 
        initialiseErrorArray(4);
    }
    
    /**
     * Resets the form after it is completed
     */
    private void resetForm() {
        course = "";
        name = "";
        department = "";
        year = 1;
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
     * Returns the course name
     * @return course name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the department in charge of the course
     * @return department
     */
    public String getDepartment() {
        return department;
    }
    
    /**
     * Returns the year of the course
     * @return course year
     */
    public String getYear() {
        return Integer.toString(year);
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
     * Sets the name of the course
     * @param name course name
     */
    public void setName(String name) {
        if (this.errorInString(name)) {
            addErrorMessage(1, "Course name is incorrect.");
        } else {
            this.name = Validator.escapeJava(name);
            setValidData(1, true);
        }
    }
    
    /**
     * Sets the department that the course is assigned
     * @param department Department in charge of the course
     */
    public void setDepartment(String department) {
        if (this.errorInString(department)) {
            addErrorMessage(2, "Department is incorrect.");
        } else {
            this.department = Validator.escapeJava(department);
            setValidData(2, true);
        }
    }
    
    /**
     * Sets the year the course occurs
     * @param year The year for the course
     */
    public void setYear(String year) {
        try {
            this.year = Integer.parseInt(year);
            setValidData(3, true);
        } catch (Exception ex) {
            addErrorMessage(3, "Year is incorrect.");
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
            db.close();
            return result;
        } else {
            return false;
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
            db.close();
            return result;
        } else {
            return false;
        }
    }
}
