package messagePackage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import toolsPackage.Database;

/**
 * Represents a message that a user may receive
 * 
 * @author John O Riordan
 */
public class Message {
    private String subject;
    private String body;
    private boolean status;
    private String location;
    private String date;
    private String time;
    private String sender;
    private MessageType type;

    public Message(String subject, String body, String location, String date, String time, boolean status, String sender, MessageType type) {
        this.subject = subject;
        this.body = body;
        this.status = status;
        this.sender = sender;
        this.type = type;
        this.time = time;
        this.location = location;
        this.date = date;
    }

    /**
     * Returns the location of the meeting in the message
     * 
     * @return Meeting location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location for the meeting
     * @param location Meeting location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the date of the meeting
     * @return Date for the meeting
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date for the meeting
     * @param date Date of the meeting
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the time of the meeting
     * @return Time the meeting is scheduled
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the time for the meeting
     * @param time Meeting time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Gets the subject for the message
     * @return Message subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the message subject line
     * @param subject Message subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets the message body
     * @return Message body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the message body
     * @param body Message body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Returns the status of the message read, unread etc.
     * @return Message status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Sets the message status
     * @param status Message status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Gets the id of the message sender
     * @return Sender id
     */
    public String getSender() {
        return sender;
    }

    /**
     * Sets the sender id
     * @param sender Sender id
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Gets the type of message
     * @return Message type
     */
    public MessageType getType() {
        return type;
    }

    /**
     * Sets the type of message
     * @param type Type of message
     */
    public void setType(MessageType type) {
        this.type = type;
    }
    
    /**
     * Creates the body of the message to be sent
     * @return Body of the message
     */
    public String createMessageBody() {
        SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObject;
        try {
            dateObject = dateTime.parse(date);
        } catch (ParseException ex) {
            dateObject = new Date();
        }
        dateTime.applyPattern("dd/MM/yyyy");
        return body + "<br>" +
               "Where: " + location + "<br>" +
               "Date: " + dateTime.format(dateObject) + "<br>" +
               "Time: " + time + "<br>";
    }
    
    /**
     * Sends the message to the list of users by inserting the information into
     * the database.
     * @param userIds user to send message 
     * @return True if successful
     */
    public boolean sendMessage(List<String> userIds) {
        boolean result = false;
        Database db = Database.getSetupDatabase();
        result = db.insert("INSERT INTO Messages (subject, messageType, body, from_uid, status)" +
                           "VALUES ('" + subject + "', '" + type.getName() + "', '" + createMessageBody() + "', '" + sender + "', " + 0 + ");");
        
        if (result) {
            int messageId = db.getPreviousAutoIncrementID("Messages");
            String insertValues = getMessageInsertValues(messageId, userIds);
            result = db.insert("INSERT INTO MessageFor (uid, messageid) VALUES " + insertValues);
        }
        
        return result;
    }
    
    /**
     * Creates an SQL insert values list of messages being sent to a list of users
     * @param messageId Message to be sent
     * @param userIds Users to send message
     * @return SQL insert values
     */
    private String getMessageInsertValues(int messageId, List<String> userIds) {
        String values = "";
        Iterator<String> ids = userIds.iterator();
        while (ids.hasNext()) {
            values += "(" + ids.next() + ", " + messageId + ")" + (ids.hasNext() ? ", " : ";");
        }
        return values;
    }
}
