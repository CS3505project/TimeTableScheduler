package groupPackage;

/**
 * Represents the types of groups in the system.
 * 
 * @author John O Riordan
 */
public enum GroupType {
    YEAR_GROUP("Year Group"),
    COLLEGE_WORK("College Work");
   
    private String name;
    
    GroupType(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of this group type
     * 
     * @return name of type
     */
    public String getName() {
        return name;
    }
    
    /**
     * Converts the string to a group type object.
     * If the string doesn't exist then the default group type is returned
     * which is COLLEGE_WORK
     * 
     * @param groupType Name of the group
     * @return Group type object
     */
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
