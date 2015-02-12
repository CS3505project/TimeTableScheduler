/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package groupPackage;

import java.util.ArrayList;
import java.util.List;
import userPackage.User;


/**
 *
 * @author jjor1 & donncha o leary
 */
public class UserGroup extends Group{
     private List<User> listOfUsers;
     
     
    /**
     * 
     * @param groupId group id from Group.java
     * @param groupName group name from Group.java
     * @param groupType group type from Group.java
     */
    public UserGroup(int groupId, String groupName, String groupType,List<User> listOfUsers){
        super(groupId,groupName,groupType);
        this.listOfUsers = new ArrayList<User>();
    }
    
    // retrive list of users from database . 
    // create user objects from each result returned from database and add them to list 
    
   
    
    
}
