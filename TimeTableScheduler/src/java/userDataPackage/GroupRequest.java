package userDataPackage;


import groupPackage.GroupType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import timeTablePackage.EventPriority;
import timeTablePackage.TimeTable;
import toolsPackage.Database;
import toolsPackage.Validator;

/**
 * A javaBean for handling requests to add a meeting
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
    
    private void resetForm() {
        groupName = "";
        groupType = GroupType.COLLEGE_WORK;
        usersInGroup = new ArrayList<String>();
        clearErrors();
    }
    
    public void setGroupName(String groupName){
        if (this.errorInString(groupName)) {
            this.addErrorMessage(0, "Group name is incorrect.");
        } else {
            this.groupName = Validator.escapeJava(groupName);
            setValidData(0, true);
        }
    }
    
    public void setGroupType(String groupType){
        this.groupType = GroupType.convertToGroupType(groupType);
    }

    public String getGroupName(){
        return Validator.unescapeJava(this.groupName);
    }

    public String getGroupType() {
        return groupType.getName();
    }
    
    public void setUsersInGroup(String[] userIds) {
        if (userIds != null) {
            for (int i = 0; i < userIds.length; i++) {
                usersInGroup.add(Validator.escapeJava(userIds[i]));
            }
        }
    }
    
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
    
    
    
    private String getGroupInsertValues(int meetingID) {
        String values = "";
        Iterator<String> userIds = usersInGroup.iterator();
        while (userIds.hasNext()) {
            values += "(" + userIds.next() + ", " + meetingID + ")" + (userIds.hasNext() ? ", " : ";");
        }
        return values;
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
