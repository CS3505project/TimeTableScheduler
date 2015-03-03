/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package userDataPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import toolsPackage.Database;

/**
 *
 * @author jjor1
 */
public class DeleteRequest {
    public boolean deleteMeeting(String meetingId, String userId) {
        boolean delete = false;
        Database db = Database.getSetupDatabase();
        
        ResultSet result = db.select("SELECT organiser_uid FROM Meeting WHERE meetingid = " + meetingId + ";");
        try {
            if (db.getNumRows(result) == 1) {
                while (result.next()) {
                    if (result.getString("organiser_uid").equals(userId)) {
                        db.insert("DELETE ");
                    }
                }
            }
        } catch (SQLException ex) {
        }
        
        return delete;
    }
}
