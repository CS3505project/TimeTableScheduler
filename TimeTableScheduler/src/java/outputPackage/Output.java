/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package outputPackage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import timeTablePackage.Day;
import timeTablePackage.EventTime;
import timeTablePackage.EventType;
import timeTablePackage.ScheduledTimeTable;
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
     * Creates the sign up page for the different types of users
     * 
     * @return HTML to create the web page
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public String createSignUp(UserType type) throws FileNotFoundException, IOException{
        String finalHTML = "";
        
        finalHTML += fileToString("commonSignUpHeader.html");
        //Switch statement with appropriate controls based on what type of user is loged in
        switch (type){
            case ADMIN:
                finalHTML += fileToString("adminSignUp.html");
            break;
            case LECTURER:
                finalHTML += fileToString("lecturerSignUp.html");
            break;
            default:
                finalHTML += fileToString("studentSignUp.html");
            break;
        }

        finalHTML += fileToString("commonSignUpFooter.html");
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
     * 
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
     * Creates the form for adding a meeting,
     * with appropriate dropdowns of groups etc. with whom to have the meeting with
     * @return A string with all the HTML for the form.
     */
    public String createMeetingForm(){
        String finalHTML = "<hgroup>\n" +
        "	<h1>Add Meeting</h1>\n" +
        "	<h2>Step 1 of 2</h2>\n" +
        "</hgroup>\n" +
        "<jsp:setProperty name=\"MeetingRequest\" property=\"*\"/>" +
        "<form>\n" +
        "	<label for=\"date\">Date:</label>\n" +
        "	<input type=\"date\" name=\"date\" id=\"date\" value=\"<%= MeetingRequest.getDate() %>\" required=\"required\"><br>\n" +
        "	<label for=\"select\">Group:</label>\n" +
        "	<select id=\"select\">\n" +
                // stuff from database
        "	</select><br>\n" +
        "	<label for=\"meetingName\">Meeting Name:</label>\n" +
        "	<input type=\"text\" name=\"meetingName\" id=\"meetingName\" value=\"<%= MeetingRequest.getMeetingName() %>\" required=\"required\"><br>\n" +
        "       <label for=\"description\">Description:</label>\n" +
        "	<input type=\"textarea\" name=\"description\" id=\"description\" value=\"<%= MeetingRequest.getDescription() %>\" required=\"required\"><br>\n" +
        "       <label for=\"priority\">Priority:</label>\n" +
        "	<input type=\"text\" name=\"priority\" id=\"priority\" value=\"<%= MeetingRequest.getPriority() %>\" required=\"required\"><br>\n" +
        "       <label for=\"time\">Time:</label>\n" +
        "	<input type=\"text\" name=\"time\" id=\"time\" value=\"<%= MeetingRequest.getTime() %>\" required=\"required\"><br>\n" +
        "	<label for=\"venue\">Venue:</label>\n" +
        "	<input type=\"text\" name=\"venue\" id=\"venue\" value=\"<%= MeetingRequest.getVenue() %>\" required=\"required\"><br>\n" +
        "	<label for=\"submit\">Submit:</label>\n" +
        "	<input type=\"submit\" id=\"submit\" value=\"Next\">\n" +
        "</form>" +
        "<p>" +
            "<%-- print errors and comit valid values to database --%>" +
            "<%= MeetingRequest.getErrors()%>" +
            "<%= MeetingRequest.createMeeting() %>" +
        "</p>";
        //add file include from htmlIncludesfolder, logic for appropopriate dropdowns etc.
        return finalHTML;
    }
    
    /**
     * Creates the form for adding a lab,
     * with appropriate dropdowns for the modules etc. depending on what the lecturer teaches
     * @return A string with all the HTML for the form.
     */
    public String createAddLabForm(){
        String finalHTML = "<hgroup>\n" +
        "	<h1>Add Lab</h1>\n" +
        "	<h2>Step 1 of 2</h2>\n" +
        "</hgroup>\n" +
        "<form>\n" +
        "	<label for=\"bday\">Day:</label>\n" +
        "	<select id=\"bday\">\n" +
        //stuff from database
        "	</select><br>\n" +
        "	<label for=\"select\">Group:</label>\n" +
        "	<select id=\"select\">\n" +
        //stuff from database
        "	</select><br>\n" +
        "	<label for=\"formText\">Module Code:</label>\n" +
        "	<input type=\"text\" name=\"text\" id=\"formtext\" required=\"required\"><br>\n" +
        "	<label for=\"venueText\">Venue:</label>\n" +
        "	<input type=\"text\" name=\"text\" id=\"venuetext\" required=\"required\"><br>\n" +
        "	<label for=\"submit\">Submit:</label>\n" +
        "	<input type=\"submit\" id=\"submit\" value=\"Next\">\n" +
        "</form>";
        //add file include from htmlIncludesfolder, logic for appropopriate dropdowns etc.
        return finalHTML;
    }
    
    /**
     * Return an unordered HTML list of the groups in the database
     * 
     * @return HTML list of groups
     */
    public String createGroupList() {
        String groups = "<ul>";
        
        Database db = Database.getSetupDatabase();
        
        // get list of all groups
        ResultSet result = db.select("SELECT groupName FROM Group;");
        try {
            while (result.next()) {
                groups += "<li>" + result.getString("groupName") + "</li>";
            }
        } catch (SQLException ex) {
            System.err.println("Error while getting group list.");
        }
        
        groups += "</ul>";
        return groups;
    }
    
    /**
     * Creates the form for creating a group,
     * @return A string with all the HTML for the form.
     */
    public String createCreateGroupForm(){
        String finalHTML = "<hgroup>\n" +
        "	<h1>Create Group</h1>\n" +
        "	<h2>Step 1 of 2</h2>\n" +
        "</hgroup>\n" +
        "<jsp:setProperty name=\"GroupRequest\" property=\"*\"/>" +
        "<form>\n" +
        "	<label for=\"gname\">Group Name:</label>\n" +
        "	<input type=\"text\" name=\"groupname\" id=\"gnametext\" value=\"<%= GroupRequest.getGroupName() %>\" required=\"required\"><br>\n" +
        "	<label for=\"submit\">Submit:</label>\n" +
        "	<input type=\"submit\" id=\"submit\" value=\"Next\">\n" +
        "</form>" +
        "<p>" +
            "<%-- print errors and comit valid values to database --%>" +
            "<%= GroupRequest.getErrors()%>" +
            "<%= GroupRequest.createGroup() %>" +
        "</p>";
        //add actual code etc
        return finalHTML;
    }
    
    /**
     * Creates the form for joining a Group,
     * with appropriate dropdowns for the public groups etc.
     * @return A string with all the HTML for the form.
     */
    public String createJoinGroupForm(String userid){
        String finalHTML = "<hgroup>\n" +
        "	<h1>Join Group</h1>\n" +
        "</hgroup>\n" +
        "<jsp:setProperty name=\"GroupRequest\" property=\"*\"/>" +
        "<form>\n" +
        "	<label for=\"gname\">Group Name:</label>\n" +
        "	<select id=\"gname\" value=\"<%= GroupRequest.getGroupName() %>\">\n" +
                //stuff from database
        "	</select><br>\n" +
        "	<label for=\"submit\">Submit:</label>\n" +
        "	<input type=\"submit\" id=\"submit\" value=\"Next\">\n" +
        "</form>" +
        "<p>" +
            "<%-- print errors and comit valid values to database --%>" +
            "<%= GroupRequest.joinGroup(" + userid + ") %>" +
        "</p>";
        //add actual code
        return finalHTML;
    }
    
    /**
    * Creates the form for joining a module,
    * with appropriate dropdowns for the public groups etc.
    * @return A string with all the HTML for the form.
    */
    public String createModuleForm(){
        String finalHTML = "<hgroup>\n" +
        "	<h1>Add Module</h1>\n" +
        "	<h2>Step 1 of 2</h2>\n" +
        "</hgroup>\n" +
        "<form>\n" +
        "	<label for=\"bday\">Day:</label>\n" +
        "	<select id=\"bday\">\n" +
        //stuff from database for dropdown goes here
        "	</select><br>\n" +
        "	<label for=\"select\">Group:</label>\n" +
        "	<select id=\"select\">\n" +
        //stuff from database for dropdown goes here
        "	<label for=\"formText\">Module Name:</label>\n" +
        "	<input type=\"text\" name=\"text\" id=\"formtext\" required=\"required\"><br>\n" +
        "	<label for=\"venueText\">Venue:</label>\n" +
        "	<input type=\"text\" name=\"text\" id=\"venuetext\" required=\"required\"><br>\n" +
        "	<label for=\"submit\">Submit:</label>\n" +
        "	<input type=\"submit\" id=\"submit\" value=\"Next\">\n" +
        "</form>";
        //add actual code
        return finalHTML;
    }
    
    /**
     * Creates a page section with the users profile image, name and details
     * @return the html representing the users profile info
     */
    public String createProfileBox(User user){        
        String finalHTML = "";
        //later edit this so that it actually gives user info
        finalHTML += "<div class='profile'>"
                + "    <div class='img'></div>"
                + "        <h1>" + user.getFirstName() + " " + user.getSurName() + "</h1>"
                + "        <h2>" + user.getEmail() + "</h2>"
                + "        <p>Hello,m I am a profile blbsjbjbv</p>"
                + "        <p>Hsdfsdf am a zzproxfile blbsjbjbv</p>"
                + "        <div>"
                + "             <h1>Some Stats Maybe</h1>"
                + "             <p>blah blah blah</p>"
                + "</div>"
                + "</div>";
        return finalHTML;
    }
    
    /**
     * Creates a dummy table with hardcoded values,
     * just for demonstration.
     * @return A string with all the HTML for the table.
     */
    public String createDummyTable(){
        String finalHTML = "";
        
        ScheduledTimeTable suggestion = new ScheduledTimeTable(EventTime.EIGHT, EventTime.EIGHTEEN, Day.MONDAY, Day.FRIDAY);
        suggestion.initialiseTimeTable(new String[]{"22"});
        suggestion.nextSuggestedTimeSlot(2, 0, true);
        finalHTML += "<h1>Timetable for this week</h1>";
        finalHTML += suggestion.displayTimeTable();
        finalHTML += "<caption>Week 4, 23/23/1234 to 12/12/1234</caption>";
        
        return finalHTML;
    }
    
    /**
     * Create the main timetable for the users home page
     * 
     * @param userID The users ID
     * @return HTML to display the timetable
     */
    public String createUserTimeTable(String userID, String filter){
        String finalHTML = "";
        
        TimeTable timetable = new TimeTable(EventTime.EIGHT, EventTime.EIGHTEEN, Day.MONDAY, Day.FRIDAY);
        timetable.addUserEvents(userID);
        finalHTML += "<h1>Timetable for this week</h1>";
        // filter menu for the timetable
        finalHTML += "<ul>" +
                         "<li><a href=\"index.jsp?filter=all\">All<a/></li>" +
                         "<li><a href=\"index.jsp?filter=lecture\">Lecture<a/></li>" +
                         "<li><a href=\"index.jsp?filter=practical\">Practical<a/></li>" +
                         "<li><a href=\"index.jsp?filter=meeting\">Meeting<a/></li>" +
                     "</ul>"; 
        finalHTML += timetable.createTimeTable(EventType.getEventType(filter));        
        return finalHTML;
    }
    
    /**
     * Create a timetable that suggests the free times for a meeting with 
     * a group of users
     * 
     * @param users The users to meet
     * @param meetingLength The duration of the meeting
     * @param overridePriority Highest priority of events that can be scheduled over
     * @param clearPreviousSuggestion Clear the most recently suggested time slot
     * @return HTML to create the timetable
     */
    public String createSuggestedTimeTable(String[] users, int meetingLength, int overridePriority, boolean clearPreviousSuggestion){
        String finalHTML = "";
        
        ScheduledTimeTable suggestion = new ScheduledTimeTable(EventTime.EIGHT, EventTime.EIGHTEEN, Day.MONDAY, Day.FRIDAY);
        suggestion.initialiseTimeTable(users);
        suggestion.nextSuggestedTimeSlot(meetingLength, overridePriority, clearPreviousSuggestion);
        finalHTML += "<h1>Timetable for this week</h1>";
        finalHTML += suggestion.displayTimeTable();
        
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
