package userDataPackage;

import toolsPackage.Database;
import toolsPackage.Validator;

/**
 * A javaBean for handling requests to add a group
 */
public final class JoinGroupRequest extends UserRequest{
    private String groupName = "";
        
    private boolean setup = false;
                
    /**
     * Default constructor
     */
    public JoinGroupRequest(){ 
        initialiseErrorArray(1);
        groupName = "";
    }
    
    /**
     * Resets the form after a successful group creation
     */
    private void resetForm() {
        groupName = "";
        clearErrors();
    }
    
    /**
     * Sets the group name for the new group
     * @param groupName Group name
     */
    public void setGroupName(String groupName){
        if (this.errorInString(groupName)) {
            this.addErrorMessage(0, "Group name is incorrect.");
        } else {
            this.groupName = Validator.escapeJava(groupName);
            setValidData(0, true);
        }
    }

    /**
     * Returns the group name
     * @return Group name
     */
    public String getGroupName(){
        return Validator.unescapeJava(this.groupName);
    }
    
    /**
     * Joins the user to this group
     * @param groupId group to be added to this user
     * @return True if added successfully
     */
    public boolean joinGroup(String groupId) {
        boolean result = false;
        if (!errorInString(groupId)) {
            Database db = Database.getSetupDatabase();
            // add the users to the group
            result = db.insert("INSERT INTO InGroup (uid, gid) "
                               + "VALUES (" + getUser().getUserID() + ", " + groupId + ");");
            
            resetForm();
            setFormLoaded(false);
            setup = false;
            db.close();
            result = true;
        }
        
        return result;
    }
}
