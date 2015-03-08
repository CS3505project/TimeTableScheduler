package userDataPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import messagePackage.Message;
import messagePackage.MessageType;
import toolsPackage.Database;

/**
 * Handles deleting a meeting for the organiser of the meeting
 * @author John O Riordan
 */
public class DeleteRequest {
    
    /**
     * Removes the meeting from the database if the information is valid
     * @param meetingId Meeting to be removed
     * @param organiser Organiser id of the meeting
     * @return True if removed successfully
     */
    public boolean deleteMeeting(String meetingId, String organiser) {
        boolean delete = false;
        Database db = Database.getSetupDatabase();
        System.out.println(meetingId + ":" + organiser);
        try {
            List<String> userIds = new ArrayList<String>();
            ResultSet users = db.select("SELECT uid FROM HasMeeting WHERE mid = " + meetingId + ";");
            while (users.next()) {
                userIds.add(users.getString("uid"));
            }
            userIds.remove(organiser);

            ResultSet meeting = db.select("SELECT * FROM Meeting WHERE meetingid = " + meetingId + ";");
        
            if (db.getNumRows(meeting) == 1) {
                while (meeting.next()) {
                    if (userIds.size() > 1) {
                        Message message = new Message("Meeting Cancelled", 
                                                      meeting.getString("description"), 
                                                      meeting.getString("room"), 
                                                      meeting.getDate("date").toString(), 
                                                      meeting.getTime("time").toString(),
                                                      false, organiser, MessageType.MEETING);
                        message.sendMessage(userIds);
                    }
                
                    if (meeting.getString("organiser_uid").equals(organiser)) {
                        delete = db.insert("DELETE FROM Meeting " +
                                            "WHERE meetingid = " + meetingId + ";");
                        delete = db.insert("DELETE FROM HasMeeting " +
                                            "WHERE mid = " + meetingId + ";");
                    }
                }
            }
        } catch (SQLException ex) {
            
        }
        db.close();
        return delete;
    }
}
