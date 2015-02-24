/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userDataPackage;

import groupPackage.Group;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;
import toolsPackage.Database;

/**
 *
 * @author cdol1
 */
public final class GroupRequest extends UserRequest{
    Group group;
    String groupName;
    
    /**
     *
     */
    public GroupRequest(){
    }
    
    /**
     * Returns the group name
     * 
     * @return group name
     */
    public String getGroupName() {
        return groupName;
    }
    
    /**
     * Validates the group name entered by the user
     * 
     * @param groupName Group name
     */
    public void setGroupName(String groupName) {
        if (!errorInString(groupName)) {
            this.groupName = groupName;
        } else {
            this.addError("Error, check the group name you entered.");
            System.err.println("Error with group name.");
        }
    }
    
    /**
     * Create a new group in the database
     * 
     * @return String to indicate success
     */
    public String createGroup() {
        String result = "Group not created.";
        
        if (!isValid()
                && executeDbQuery("INSERT INTO Groups (groupName, groupType) " 
                                  + "VALUES ("+ groupName + "\", \"group\");")) {
            result = "Group created.";
        }
        
        return result;
    }
    
    /**
     * Add a users to the specified group
     * 
     * @param userid user ids to add
     * @return String to indicate success
     */
    public String joinGroup(String userid) {
        String result = "Not joined group.";
        
        if (!isValid()) {
            Database db = Database.getSetupDatabase();
            
            ResultSet groups = db.select("SELECT gid FROM Group" +
                                       "WHERE groupName = \"" + groupName + "\"");
            
            //TO-DO
            
            // add the users to the group
            db.insert("INSERT INTO InGroup (uid, gid) "
                + "VALUES (" + userid + "\", " + groupName + ");");
            
            db.close();
            result = "Joined group.";
        }
        
        return result;
    }
}
