/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package groupPackage;

/**
 *
 * @author jjor1
 */
public enum GroupType {
    YEAR_GROUP("Year Group"),
    COLLEGE_WORK("College Work");
   
    private String name;
    
    GroupType(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public static GroupType convertToGroupType(String groupType) {
        GroupType type = GroupType.COLLEGE_WORK;
        for (GroupType curType : GroupType.values()) {
            if (curType.name.equals(groupType)) {
                type = curType; 
            }
        }
        return type;
    }
}
