package groupPackage;

import toolsPackage.Database;

/**
 *
 * @author jjor1 & donncha o leary
 */
public class Group {
    private int groupId;
    private String groupName;
    private String groupType;
    
    public Group(int groupId, String groupName, String groupType){
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupType = groupType;
    }
    /**
     * 
     * @return groups number 
     * 
     */
    public int getGroupId(){
        return groupId;
    }
    
    /**
     * 
     * @param groupId 
     */
    public void setGroupId(int groupId){
        this.groupId = groupId;
    }
    
    /**
     * 
     * @return name oof group
     */
    public String getGroupName(){
        return groupName;
    }
    
    /**
     * 
     * @param groupName name of group
     */
    public void setGroupName(String groupName){
        this.groupName = groupName;
    }
    
    /**
     * 
     * @return type of group
     */
    public String getGroupType(){
        return groupType;
    }
    
    /**
     * 
     * @param groupType the type of group
     */
    public void setGroupType(String groupType){
        this.groupType = groupType;
    }
    
    
    /**
     * Executes the querys to the database that don't return results
     * 
     * @param insertSQL The sql query to execute
     * @return True if query was successful
     */
    public boolean executeDbQuery(String insertSQL) {
        //create db
        Database db = new Database();
        //get access info from properties
        db.setupFromPropertiesFile("database.properties");
        
        String[] users = db.selectColumn(insertSQL);
        
        
        db.close();
        return false;
    }
    
}
