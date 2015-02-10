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
import timeTablePackage.TimeTable;

/**
 *
 * @author lam1
 */
public class Output {
    
    HttpServletRequest request;
    String userType = "";//placeholder
    
    public Output(HttpServletRequest request){
        this.request = request;
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
            case "admin":
            break;
            case "lecturer":
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
     * Creates the form for adding a meeting,
     * with appropriate dropdowns of groups etc. with whom to have the meeting with
     * @return A string with all the HTML for the form.
     */
    public String createMeetingForm(){
        String finalHTML = "";
        //add file include from htmlIncludesfolder, logic for appropopriate dropdowns etc.
        return finalHTML;
    }
    
    /**
     * Creates the form for adding a lab,
     * with appropriate dropdowns for the modules etc. depending on what the lecturer teaches
     * @return A string with all the HTML for the form.
     */
    public String createAddLabForm(){
        String finalHTML = "";
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
        String finalHTML = "";
        //add actual code
        return finalHTML;
    }
    
    /**
    * Creates the form for joining a module,
    * with appropriate dropdowns for the public groups etc.
    * @return A string with all the HTML for the form.
    */
    public String createModuleForm(){
        String finalHTML = "";
        //add actual code
        return finalHTML;
    }
    
    /**
     * Creates a dummy table with hardcoded values,
     * just for demonstration.
     * @return A string with all the HTML for the table.
     */
    public String createDummyTable(){
        String finalHTML = "";
        
        // Test method to generate timetable HTML from actual event objects
        TimeTable timetable = new TimeTable();
        timetable.addDummyEvents();
        finalHTML += "<h1>Timetable for this week</h1>";
        finalHTML += timetable.createTimeTable(EventTime.EIGHT, EventTime.EIGHTEEN, Day.MONDAY, Day.FRIDAY);
        finalHTML += "<caption>Week 4, 23/23/1234 to 12/12/1234</caption>";
       
        /*finalHTML = "<h1>Timetable for this week</h1>\n" +
    "			<table>\n" +
    "				<tr><th></th><th>9:00</th><th>10:00</th><th>11:00</th><th>12:00</th><th>13:00</th><th>14:00</th><th>15:00</th><th>16:00</th><th>17:00</th></tr>\n" +
    "				<tr><th>Mon</th><td class=\"meeting\">meet with blah</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>\n" +
    "				<tr><th>Tue</th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>\n" +
    "				<tr><th>Wed</th><td class =\"lecture\">CS1234, G04</td><td></td><td class=\"practical\">CS1234 Lab</td><td></td><td></td><td></td><td></td><td></td><td></td></tr>\n" +
    "				<tr><th>Thu</th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>\n" +
    "				<tr><th>Fri</th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>\n" +
    "				 <caption>Week 4, 23/23/1234 to 12/12/1234</caption>\n" +
    "			</table>";*/
        
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
