package groupPackage;

import java.util.List;

/**
 *
 * @author jjor1 & donncha o leary
 */
public class ModuleGroup extends Group{
    private List<String> listOfUsers;
    
    /**
     * 
     * @param groupId groupId group id from Group.java
     * @param groupName group name from Group.java
     * @param groupType group type from Group.java
     */
    public ModuleGroup(int groupId, String groupName, String groupType){
        super(groupId,groupName,groupType);
    }
    
    
    public boolean retriveModulesFromDB(){
       this.executeDbQuery("");
       return false;
   } 
    
    
}
