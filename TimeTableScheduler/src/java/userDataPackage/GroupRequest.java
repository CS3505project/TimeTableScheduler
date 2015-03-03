package userDataPackage;

import groupPackage.GroupType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import toolsPackage.Database;
import toolsPackage.Validator;

/**
 * A javaBean for handling requests to add a group
 */
public final class GroupRequest extends UserRequest{
    private String groupName = "";
    private GroupType groupType = GroupType.COLLEGE_WORK;
    
    private List<String> usersInGroup = new ArrayList<String>();
    
    private boolean setup = false;
                
    /**
     * Default constructor
     */
    public GroupRequest(){ 
        initialiseErrorArray(1);
        groupName = "";
        groupType = GroupType.COLLEGE_WORK;
        usersInGroup = new ArrayList<String>();
    }
    
    /**
     * Resets the form after a successful group creation
     */
    private void resetForm() {
        groupName = "";
        groupType = GroupType.COLLEGE_WORK;
        usersInGroup = new ArrayList<String>();
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
     * Sets the group type
     * @param groupType Group type
     */
    public void setGroupType(String groupType){
        this.groupType = GroupType.convertToGroupType(groupType);
    }

    /**
     * Returns the group name
     * @return Group name
     */
    public String getGroupName(){
        return Validator.unescapeJava(this.groupName);
    }

    /**
     * Returns the group type
     * @return Group type
     */
    public String getGroupType() {
        return groupType.getName();
    }
    
    /**
     * Sets the users to be added to the group
     * @param userIds users to be added to the group
     */
    public void setUsersInGroup(String[] userIds) {
        if (userIds != null) {
            for (int i = 0; i < userIds.length; i++) {
                usersInGroup.add(Validator.escapeJava(userIds[i]));
            }
        }
    }
    
    /**
     * Creates the group
     * @return True if created successfully
     */
    public boolean createGroup() {
        if (isValid()) {
            boolean result = false;
            
            Database db = Database.getSetupDatabase();
 
            result = db.insert("INSERT INTO Groups (groupName, groupType) " 
                                  + "VALUES (\""+ groupName + "\", \"" + groupType.getName() + "\");");

            usersInGroup.add(getUser().getUserID());
            String values = getGroupInsertValues(db.getPreviousAutoIncrementID("Groups")); 
            if (!values.equals("")) {
                result = db.insert("INSERT INTO InGroup (uid, gid) VALUES " + values);
            }
            
            resetForm();
            setup = false;
            db.close();
            return result;
        } else {
            return false;
        }
    }
    
    /**
     * Gets the SQL list of values to be inserted
     * @param meetingID meeting id to add each user to
     * @return SQL values list to be inserted
     */
    private String getGroupInsertValues(int meetingID) {
        String values = "";
        Iterator<String> userIds = usersInGroup.iterator();
        while (userIds.hasNext()) {
            values += "(" + userIds.next() + ", " + meetingID + ")" + (userIds.hasNext() ? ", " : ";");
        }
        return values;
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
            setup = false;
            db.close();
            result = true;
        }
        
        return result;
    }
}
