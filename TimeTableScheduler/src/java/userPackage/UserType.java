package userPackage;

/**
 * Represents the different types of users in the system
 * 
 * @author John O Riordan
 */
public enum UserType {
    LECTURER("lecturer"),
    ADMIN("admin"),
    STUDENT("student");
    
    private String name;
    
    UserType(String name) {
        this.name = name;
    }
    
    /**
     * Returns the enum that represents the string
     * 
     * @param user User string
     * @return Enum for the user
     */
    public static UserType getUserType(String user) {
        UserType eventType = UserType.STUDENT;
        for (UserType type : UserType.values()) {
            if (type.name.equals(user)) {
                eventType = type;
            }
        }
        return eventType;
    }
}
