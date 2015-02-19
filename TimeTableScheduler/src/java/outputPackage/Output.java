/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package outputPackage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import javax.servlet.http.HttpServletRequest;
import timeTablePackage.Day;
import timeTablePackage.EventTime;
import timeTablePackage.ScheduledTimeTable;
import timeTablePackage.TimeTable;
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
    
    public String createSignUp() throws FileNotFoundException, IOException{
        String finalHTML = "";
        
        finalHTML += fileToString("commonSignUpHeader.html");
        //Switch statement with appropriate controls based on what type of user is loged in
        switch (this.userType){
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
        "<form>\n" +
        "	<label for=\"bday\">Date:</label>\n" +
        "	<input type=\"date\" name=\"bday\" id=\"bday\"  required=\"required\"><br>\n" +
        "	<label for=\"select\">Group:</label>\n" +
        "	<select id=\"select\">\n" +
                // stuff from database
        "	</select><br>\n" +
        "	<label for=\"formText\">Meeting Name:</label>\n" +
        "	<input type=\"text\" name=\"meeting-name\" id=\"formtext\" required=\"required\"><br>\n" +
        "	<label for=\"venueText\">Venue:</label>\n" +
        "	<input type=\"text\" name=\"venue\" id=\"venuetext\" required=\"required\"><br>\n" +
        "	<label for=\"submit\">Submit:</label>\n" +
        "	<input type=\"submit\" id=\"submit\" value=\"Next\">\n" +
        "</form>";
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
     * Creates the form for creating a group,
     * @return A string with all the HTML for the form.
     */
    public String createCreateGroupForm(){
        String finalHTML = "<hgroup>\n" +
        "	<h1>Create Group</h1>\n" +
        "	<h2>Step 1 of 2</h2>\n" +
        "</hgroup>\n" +
        "<form>\n" +
        "	<label for=\"gname\">Group Name:</label>\n" +
        "	<input type=\"text\" name=\"text\" id=\"gnametext\" required=\"required\"><br>\n" +
        "	<label for=\"submit\">Submit:</label>\n" +
        "	<input type=\"submit\" id=\"submit\" value=\"Next\">\n" +
        "</form>";
        //add actual code etc
        return finalHTML;
    }
    
    /**
     * Creates the form for joining a Group,
     * with appropriate dropdowns for the public groups etc.
     * @return A string with all the HTML for the form.
     */
    public String createJoinGroupForm(){
        String finalHTML = "<hgroup>\n" +
        "	<h1>Join Group</h1>\n" +
        "</hgroup>\n" +
        "<form>\n" +
        "	<label for=\"gname\">Group Name:</label>\n" +
        "	<select id=\"gname\">\n" +
                //stuff from database
        "	</select><br>\n" +
        "	<label for=\"submit\">Submit:</label>\n" +
        "	<input type=\"submit\" id=\"submit\" value=\"Next\">\n" +
        "</form>";
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
    public String createProfileBox(){
        String finalHTML = "";
        //later edit this so that it actually gives user info
        finalHTML += "<div class='profile'>"
                + "    <div class='img'></div>"
                + "        <h1>Actual McName</h1>"
                + "        <h2>email123@mail.com.ie</h2>"
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
        suggestion.initialiseTimeTable(new String[]{"1", "2"});
        suggestion.nextSuggestedTimeSlot(2, 0, true);
        finalHTML += "<h1>Timetable for this week</h1>";
        finalHTML += suggestion.displayTimeTable();
        finalHTML += "<caption>Week 4, 23/23/1234 to 12/12/1234</caption>";
       
 
        
        return finalHTML;
    }
    
    public String createUserTimeTable(String userID){
        String finalHTML = "";
        
        TimeTable timetable = new TimeTable(EventTime.EIGHT, EventTime.EIGHTEEN, Day.MONDAY, Day.FRIDAY);
        timetable.addUserEvents(userID);
        finalHTML += "<h1>Timetable for this week</h1>";
        finalHTML += timetable.createTimeTable();        
        return finalHTML;
    }
    
    public String createSuggestedTimeTable(String[] users, int meetingLength, int overridePriority, boolean clearPreviousSuggestion){
        String finalHTML = "";
        
        ScheduledTimeTable suggestion = new ScheduledTimeTable(EventTime.EIGHT, EventTime.EIGHTEEN, Day.MONDAY, Day.FRIDAY);
        suggestion.initialiseTimeTable(users);
        suggestion.nextSuggestedTimeSlot(meetingLength, overridePriority, clearPreviousSuggestion);
        finalHTML += "<h1>Timetable for this week</h1>";
        finalHTML += suggestion.displayTimeTable();
        finalHTML += "<caption>Week 4, 23/23/1234 to 12/12/1234</caption>";
        
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
