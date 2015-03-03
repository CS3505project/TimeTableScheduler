package outputPackage;

import groupPackage.GroupType;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.http.HttpServletRequest;
import messagePackage.MessageType;
import timeTablePackage.EventPriority;
import timeTablePackage.EventType;
import timeTablePackage.TimeTable;
import toolsPackage.Database;
import userPackage.User;
import userPackage.UserType;
import static userPackage.UserType.ADMIN;
import static userPackage.UserType.LECTURER;

/**
 *
 * @author lam1
 */
public class Output {
    HttpServletRequest request;
    UserType userType;
    
    /**
     * Creates an output object
     * If the userType is null then the default is used STUDENT
     * 
     * @param request
     * @param userType user type for displaying certain forms etc.
     */
    public Output(HttpServletRequest request, UserType userType){
        this.request = request;
        this.userType = (userType == null ? userType.STUDENT : userType);
    }
    
    /**
     * Creates the header which occurs in each page, with side bar etc.
     * @return A string with all the HTML.
     */
    public String createHeader() throws FileNotFoundException, IOException{
        String finalHTML = "";
        
        finalHTML += fileToString("commonHeader.html");
        //Switch statement with appropriate controls based on what type of user is loged in
        switch (this.userType){
            case ADMIN:
                finalHTML += fileToString("adminMenu.html");
            break;
            case LECTURER:
                finalHTML += fileToString("lecturerMenu.html");
            break;
            default:
                finalHTML += fileToString("studentMenu.html");
            break;
        }

        finalHTML += fileToString("commonHeaderEnd.html");
        return finalHTML;
    }
    
    /**
     * Creates the footer which is on each page.
     * @return A string with all the HTML.
     */
    public String createFooter(){
        String finalHTML = "";
        finalHTML += fileToString("commonEnd.html");
        return finalHTML;
    }
    
    /**
     * Creates the login page
     * @return html for the login page
     */
    public String createLogin(){
        String finalHTML = "";
        finalHTML += fileToString("login.html");
        return finalHTML;
    }
    /**
     * Creates the html for a logout page.
     * @return the html as a string.
     */
    public String createLogout(){
        String finalHTML = "";
        finalHTML += fileToString("logout.html");
        return finalHTML;
    }
    
    /**
     * Creates a nav with two buttons to display the timetable for next
     * week or last week
     * 
     * @param cal Calendar to get the dates for the next and last week
     * @param request Used to construct the url used in the nav buttons
     * @return html links to display the next and last week of events
     */
    public String createTimeTableNav(Calendar cal, HttpServletRequest request) {
        String html = "<ul class='weekSelect'>";
                
        // Print dates of the current week starting on Monday
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        cal.add(Calendar.DATE, -7);
        String startDate = df.format(cal.getTime());
        cal.add(Calendar.DATE, 14);
        String endDate = df.format(cal.getTime());
        cal.add(Calendar.DATE, -7);
                
        // reconstruct url to and add the date attributes to it
        StringBuffer startUrl = request.getRequestURL().append("?");
        Map<String, String[]> paramMap = request.getParameterMap();
        for (String key : paramMap.keySet()) {
            for (String value : paramMap.get(key)) {
                if (!key.equals("displayDate")) {
                    startUrl.append(key).append("=").append(value).append("&");
                }
            }
        }
        
        html += "<li><a href=\"" + startUrl.toString() + "displayDate=" + startDate + "\">Last Week</a></li>";
        html += "<li><a href=\"" + startUrl.toString() + "displayDate=" + endDate + "\">Next Week</a></li></ul>";
        
        return html;
    }
    
    /**
     * Creates an error section to display errors from a form
     * 
     * @param numErrors number of error messages
     * @param errorMessage Messages to display
     * @return html to display the errors in a div
     */
    public String displayErrors(int numErrors, String errorMessage) {
        return "<div class=\"errors\">" +
                    "<h1><span>" + numErrors + "</span></h1>" +
                    "<p>" +
                        errorMessage +
                    "</p>" +
               "</div>";
    }
    
    /**
     * Return an HTML list of the groups
     * 
     * @return HTML list of groups
     */
    public String createGroupList(String userid) {
        String groups = "<h1 class='banner'>Your Groups</h1><div><ul>";
        
        Database db = Database.getSetupDatabase();
        
        // get list of all groups
        ResultSet result = db.select("SELECT * FROM Groups " +
                                      "WHERE groupid IN " +
                                           "(SELECT gid " +
                                           "FROM InGroup " +
                                           "WHERE uid = " + userid + ");");
        try {
            while (result.next()) {
                groups += "<li>" + result.getString("groupName") + "</li>";
            }
        } catch (SQLException ex) {
            System.err.println("Error while getting group list.");
        }
        
        groups += "</ul></div>";
        return groups;
    }
    
    /**
     * Creates a html drop down for all year groups in the system
     * @return html for the drop down
     */
    public String createYearGroupList() {
        String groups = "";
        
        Database db = Database.getSetupDatabase();
        
        // get list of all groups
        ResultSet result = db.select("SELECT * FROM Groups WHERE groupType = \"" + GroupType.YEAR_GROUP.getName() + "\";");
        try {
            while (result.next()) {
                groups += "<option value=\"" + result.getString("groupid") + "\">" + result.getString("groupName") + "</option>";
            }
        } catch (SQLException ex) {
            System.err.println("Error while getting group list.");
        }
        
        groups += "";
        return groups;
    }
    
    /**
     * Create a drop down list of all the groups in the system
     * @return html for the drop down
     */
    public String createAllGroupList() {
        String groups = "";
        
        Database db = Database.getSetupDatabase();
        
        // get list of all groups
        ResultSet result = db.select("SELECT * FROM Groups;");
        try {
            while (result.next()) {
                groups += "<option value=\"" + result.getString("groupid") + "\">" + result.getString("groupName") + "</option>";
            }
        } catch (SQLException ex) {
            System.err.println("Error while getting group list.");
        }
        
        groups += "";
        return groups;
    }
    
    /**
     * Create a drop down list of all the courses in the system
     * @return html drop down list
     */
    public String createCourseList() {
        String groups = "";
        
        Database db = Database.getSetupDatabase();
        
        // get list of all groups
        ResultSet result = db.select("SELECT * FROM Course;");
        try {
            while (result.next()) {
                groups += "<option value=\"" + result.getString("id") + "\">" + result.getString("courseCode") + " : " + result.getString("name") + " (Year: " + result.getString("year") + ")</option>";
            }
        } catch (SQLException ex) {
            System.err.println("Error while getting group list.");
        }
        
        groups += "";
        return groups;
    }
    
    /**
     * Create a drop down list for the groups that the user is not a part of.
     * @param userid user id to select from the groups
     * @return html drop down
     */
    public String createJoinGroupDropDown(String userid) {
        String finalHTML = "";
        Database db = Database.getSetupDatabase();
        
        ResultSet result = db.select("SELECT * " +
                                    "	FROM Groups " +
                                    "	WHERE groupid NOT IN " +
                                    "		(SELECT gid " +
                                    "		FROM InGroup " +
                                    "		WHERE uid = " + userid + ");");
        try {
            while (result.next()) {
                finalHTML += "<option value=\"" + result.getString("groupid") + "\">" + result.getString("groupName") + "</option>";
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving group list");
        }
        
        return finalHTML;
    }
    
    /**
     * Create a drop down list of priority values in the system
     * @return html drop down list
     */
     public String createPriorityDropDown() {
        String finalHTML = "";
        for (EventPriority priority : EventPriority.values()) {
            finalHTML += "<option value=\"" + priority.getPriorityName() + "\">" + priority.getPriorityName() + "</option>";
        }
        return finalHTML;
    }
    
    /**
     * Creates a HTML drop down list of group names
     * 
     * @param userid User in the groups
     * @return html of the drop down
     */
    public String createGroupDropDown(String userid) {
        String finalHTML = "";
        Database db = Database.getSetupDatabase();
        
        ResultSet result = db.select("SELECT * " +
                                    "	FROM Groups " +
                                    "	WHERE groupid IN " +
                                    "		(SELECT gid " +
                                    "		FROM InGroup " +
                                    "		WHERE uid = " + userid + ") " +
                                    "			AND grouptype != \"" + GroupType.YEAR_GROUP.getName() + "\";");
        try {
            while (result.next()) {
                finalHTML += "<option value=\"" + result.getString("groupid") + "\">" + result.getString("groupName") + "</option>";
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving group list");
        }
        
        return finalHTML;
    }

    /**
     * Creates a page section with the users messages
     * @return the html representing the messages
     */
    public String createMessages(User user){        
        String finalHTML = "";
        finalHTML += "<h1 class='banner'>Messages</h1>";
        
        Database db = Database.getSetupDatabase();
        
        ResultSet result = db.select("SELECT * " +
                                    "FROM Messages " +
                                    "WHERE messageid IN " +
                                    "( " +
                                    "SELECT messageid " +
                                    "FROM MessageFor " +
                                    "WHERE uid = " + user.getUserID() + ");");
        try {
            while (result.next()) {
                finalHTML += "<div class='message'>" 
                            + "<span class='" + result.getString("messageType") + "'>" + result.getString("messageType") + "</span>"
                            + "<h1>Subject: " + result.getString("subject") + "</h1>"
                            + "<p>Message: " + result.getString("body") + "</p><br>"
                            + "<input type='checkbox' name='accept' value='notificationNumber'></div>";
            }
        } catch (SQLException ex) {
            
        }
        
        return finalHTML;
    }
    /**
     * Creates a dummy table with hardcoded values,
     * just for demonstration.
     * @return A string with all the HTML for the table.
     */
    public String createDummyTable(){
        String finalHTML = "";
        
        TimeTable suggestion = TimeTable.getPreSetTimeTable();
        suggestion.initialiseTimeTable(Arrays.asList("1", "9"));
        //suggestion.nextSuggestedTimeSlot(2, 0, true);
        finalHTML += "<h1>Availability</h1>";
        finalHTML += suggestion.createTimeTable();
        
        return finalHTML;
    }
    /**
     * finds all the appropriate groups etc that a user can have meetings with and puts them in seperate lists
     * @param user User to create the form
     * @return the html for the dropdown and radio selector 
     */
    public String createMeetingFormGroupDropdown(User user){
        String finalHTML = "";
        finalHTML += "<div class='radioBox'><span>Group</span><input type='radio' id='groupRadio' name='withType' value='group' checked>\n" +
        "<span>Individual</span><input type='radio' id='individualRadio' name='withType' value='individual'>\n" +
        "<span>Personal</span><input type='radio' id='personalRadio' name='withType' value='personal'></div>\n" +
        "<div id='groupSelectDiv'><label for='groupSelect'>With Group:</label><select name=\"groupID\" id='groupSelect'>\n";
        //for loop for creating options for legit groups
        finalHTML += createGroupDropDown(user.getUserID());
        
        finalHTML += "</select></div>\n" +
        "<div id='individualSelectDiv'><label for='individualSelect'>With:</label><select name=\"individualID\" id='individualSelect' disabled></div>\n";
        //for loop for creating options for legit groups
        finalHTML += createIndividualDropDown(user.getUserID());
        
        finalHTML += "</select></div>";
        return finalHTML;
    }
    
    /**
     * finds all the appropriate groups etc that a user can have meetings with and puts them in seperate lists
     * @param user User to create the form
     * @return the html for the dropdown and radio selector 
     */
    public String createAdminMeetingFormDropdown(){
        String finalHTML = "";
        finalHTML += "<div class='radioBox'><span>Group</span><input type='radio' id='groupRadio' name='withType' value='group' checked>\n" +
        "<span>Individual</span><input type='radio' id='individualRadio' name='withType' value='individual'>\n" +
        "<span>Personal</span><input type='radio' id='personalRadio' name='withType' value='personal'></div>\n" +
        "<div id='groupSelectDiv'><label for='groupSelect'>With Group:</label><select name=\"groupID\" id='groupSelect'>\n";
        //for loop for creating options for legit groups
        finalHTML += createAllGroupList();
        
        finalHTML += "</select></div>\n" +
        "<div id='individualSelectDiv'><label for='individualSelect'>With:</label><select name=\"individualID\" id='individualSelect' disabled></div>\n";
        //for loop for creating options for legit groups
        finalHTML += createAllIndividualDropDown();
        
        finalHTML += "</select></div>";
        return finalHTML;
    }
    
    public String createAllIndividualDropDown() {
        String finalHTML = "";
        Database db = Database.getSetupDatabase();
        
        ResultSet result = db.select("SELECT uid, firstname, surname, studentid as 'id' " +
                                    "FROM Student JOIN User " +
                                    "ON Student.uid = userid " +
                                    "UNION " +
                                    "SELECT uid, firstname, surname, lecturerid as 'id' " +
                                    "FROM Lecturer JOIN User " +
                                    "ON Lecturer.uid = userid;");
        try {
            while (result.next()) {
                finalHTML += "<option value=\"" + result.getString("uid") + "\">" 
                        + result.getString("id") + " : " + result.getString("firstName") + " " 
                        + result.getString("surname") + "</option>";
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving group list");
        }
        
        return finalHTML;
    }
    
    /**
     * Create the drop down list of the modules for this user
     * @param userid The user
     * @return drop down list
     */
    public String createModuleDropDown() {
        String finalHTML = "<select name=\"moduleCode\">";
        
        Database db = Database.getSetupDatabase();
        
        ResultSet result = db.select("SELECT * FROM Module");
        try {
            while (result.next()) {
                finalHTML += "<option value=\"" + result.getString("moduleCode") + "\">"
                             + result.getString("moduleCode") + ": " + result.getString("name")
                             + "</option>";
            }
        } catch (SQLException ex) {
        }
        
        db.close();
        
        finalHTML += "</select>";
        return finalHTML;
    }
    
    /**
     * Create a drop down list for the group types in the system
     * @return html drop down
     */
    public String createGroupTypeDropDown() {
        String finalHTML = "";
        
        for (GroupType type : GroupType.values()) {
            finalHTML += "<option value=\"" + type.getName() + "\">"
                         + type.getName() + "</option>";
        }
        
        return finalHTML;
    }
    
    /**
     * Creates a drop down list of users that this user can scheduled a meeting with.
     * @param userid user organising the meeting
     * @return html drop down
     */
    public String createIndividualDropDown(String userid) {
        String finalHTML = "";
        Database db = Database.getSetupDatabase();
        
        ResultSet result = db.select("SELECT uid, firstname, surname, studentid as 'id' " +
                                    "FROM Student JOIN User " +
                                    "ON Student.uid = userid " +
                                    "WHERE Student.uid IN " +
                                    "	(SELECT uid " +
                                    "	FROM InGroup " +
                                    "	WHERE gid IN " +
                                    "		(SELECT gid " +
                                    "		FROM InGroup " +
                                    "		WHERE uid = " + userid + ")" +
                                    "	AND uid != " + userid + ") " +
                                    "UNION " +
                                    "SELECT uid, firstname, surname, lecturerid as 'id' " +
                                    "FROM Lecturer JOIN User " +
                                    "ON Lecturer.uid = userid " +
                                    "WHERE Lecturer.uid IN " +
                                    "	(SELECT uid " +
                                    "	FROM InGroup " +
                                    "	WHERE gid IN " +
                                    "		(SELECT gid " +
                                    "		FROM InGroup " +
                                    "		WHERE uid = " + userid + ")" +
                                    "	AND uid != " + userid + ");");
        try {
            while (result.next()) {
                finalHTML += "<option value=\"" + result.getString("uid") + "\">" 
                        + result.getString("id") + " : " + result.getString("firstName") + " " 
                        + result.getString("surname") + "</option>";
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving group list");
        }
        
        return finalHTML;
    }
    
    /**
     * Create the main timetable for the users home page
     * 
     * @param timeTable The time to display
     * @param filter Filter events to display
     * @param clickable Sets whether the timetable is interactive for certain forms
     * @return HTML to display the timetable
     */
    public String createUserTimeTable(TimeTable timeTable, String filter, boolean clickable, String userId){
        String finalHTML = "";
        
        finalHTML += "<h1  class='banner'>Timetable for this week</h1>";
        // filter menu for the timetable
        finalHTML += "<ul class=\"filters\">" +
                         "<li><a href=\"index.jsp?filter=all\">All<a/></li>" +
                         "<li><a href=\"index.jsp?filter=lecture\">Lecture<a/></li>" +
                         "<li><a href=\"index.jsp?filter=practical\">Practical<a/></li>" +
                         "<li><a href=\"index.jsp?filter=meeting\">Meeting<a/></li>" +
                     "</ul>"; 
        finalHTML += timeTable.createTimeTable(EventType.getEventType(filter), clickable, userId); 
        
        return finalHTML;
    }
    
    /**
     * Create a timetable that suggests the free times for a meeting with 
     * a group of users
     * 
     * @param suggestion Timetable to display
     * @param meetingLength The duration of the meeting
     * @param priority Highest priority of events that can be scheduled over
     * @param clearPrevSuggestion Clear the most recently suggested time slot
     * @return HTML to create the timetable
     */
    public String createSuggestedTimeTable(TimeTable suggestion, int meetingLength, int priority, boolean clearPrevSuggestion){
        String finalHTML  = "";
        suggestion.nextSuggestedTimeSlot(meetingLength, priority, clearPrevSuggestion);
        finalHTML += "<h1>Availability</h1>";
        finalHTML += suggestion.createTimeTable();
        
        return finalHTML;
    }
    
    /**
     * A helper method for generating the html for a single message
     * Takes in a notification object and generates a html representation of it
     */
    private String createMessageBox(String messageType){
        String finalHTML = "";
        finalHTML += "<div class='message'>";
        switch(messageType){
            case "meeting":
                finalHTML += "<span class='meeting'>Meeting</span>"
                        + "<h1></h1>"
                        + "<p></p>"
                        + "<p></p>"
                        + "<input type='checkbox' name='accept' value='notificationNumber'>";
                break;
            default:
                break;
        }
        finalHTML += "</div>";
        return finalHTML;
    }
    /**
     * A helper method for including the contents of a file.
     * @param fileName The name of the file to retrieve the contents of.
     * @return A string containing the contents.
     */
    private String fileToString(String fileName){
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        String contents = new Scanner(inStream).useDelimiter("\\Z").next();
        return contents;
    }
}
