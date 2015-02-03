/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userPackage;

/**
 *
 * @author John O Riordan
 */
public class Admin extends User {

    public Admin(String email, String firstName, String surName, String userID) {
        super(email, firstName, surName, userID);
    }
    
    public boolean removeUser() {
        return false;
    }
    
    public boolean deleteEntry() {
        return false;
    }
    
    public boolean validateLecturer() {
        return false;
    }
    
    public boolean backupDatabse() {
        return false;
    }
}
