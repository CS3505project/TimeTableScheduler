/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package outputPackage;

import java.io.File;
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
    
    public Output(HttpServletRequest request){
        this.request = request;
    }
    
    /**
     * Creates the header which occurs in each page, with side bar etc.
     * @return A string with all the HTML.
     */
    public String createHeader() throws FileNotFoundException, IOException{
        String finalHTML = "";
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("commonHeader.html");
        finalHTML += new Scanner(inStream).useDelimiter("\\Z").next();
        //add file include from htmlIncludesfolder, conditional logic based on user type etc.
        
        /*
         * Test need method to generate timetable HTML from actual event objects
         * 
         * TimeTable timetable = new TimeTable();
         * timetable.addDummyEvents();
         * finalHTML += timetable.createTimeTable(EventTime.EIGHT, EventTime.EIGHTTEEN, Day.MONDAY, Day.FRIDAY);
         */
        
        return finalHTML;
    }
    
    /**
     * Creates the footer which is on each page.
     * @return A string with all the HTML.
     */
    public String createFooter(){
        String finalHTML = "";
        //add file include from htmlIncludesfolder
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
        String finalHTML = "";
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
     * Creates a dummy table with hardcoded values,
     * just for demonstration.
     * @return A string with all the HTML for the table.
     */
    public String createDummyTable(){
        String finalHTML;
        finalHTML = "<h1>Timetable for this week</h1>\n" +
    "			<table>\n" +
    "				<tr><th></th><th>9:00</th><th>10:00</th><th>11:00</th><th>12:00</th><th>13:00</th><th>14:00</th><th>15:00</th><th>16:00</th><th>17:00</th></tr>\n" +
    "				<tr><th>Mon</th><td class=\"meeting\">meet with blah</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>\n" +
    "				<tr><th>Tue</th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>\n" +
    "				<tr><th>Wed</th><td class =\"lecture\">CS1234, G04</td><td></td><td class=\"practical\">CS1234 Lab</td><td></td><td></td><td></td><td></td><td></td><td></td></tr>\n" +
    "				<tr><th>Thu</th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>\n" +
    "				<tr><th>Fri</th><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>\n" +
    "				 <caption>Week 4, 23/23/1234 to 12/12/1234</caption>\n" +
    "			</table>";
        return finalHTML;
    }
}
