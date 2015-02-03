/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userPackage;

/**
 * Administrator for the system
 * 
 * @author John O Riordan
 */
public final class Admin extends User {

    public Admin(String email, String firstName, String surName, String userID) {
        super(email, firstName, surName, userID);
    }
    
    /**
     * Remove a user from the database
     * 
     * @return True if the user is removed successfully
     */
    public boolean removeUser() {
        return false;
    }
    
    /**
     * Validate whether a lecturer is actually a lecturer
     * 
     * @return True if the lecturer is valid
     */
    public boolean validateLecturer() {
        return false;
    }
    
    /**
     * Perform a backup of the system
     */
    public void backupDatabse() {
    }
}
