/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package outputPackage;

/**
 *
 * @author lam1
 */
public class Output {
    /**
     * Creates the header which occurs in each page, with side bar etc.
     * @return A string with all the HTML.
     */
    public String createHeader(){
        String finalHTML = "";
        //add file include from htmlIncludesfolder, conditional logic based on user type etc.
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
}
