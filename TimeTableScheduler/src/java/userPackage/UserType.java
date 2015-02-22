/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    public static UserType getUserType(String user) {
        UserType eventType = null;
        for (UserType type : UserType.values()) {
            if (type.name.equals(user)) {
                eventType = type;
            }
        }
        return eventType;
    }
}
