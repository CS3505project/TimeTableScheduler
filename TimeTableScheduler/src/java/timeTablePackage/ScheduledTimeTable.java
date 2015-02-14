/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import static timeTablePackage.TimeTable.DAY_INDEX;
import static timeTablePackage.TimeTable.HOUR_INDEX;
import toolsPackage.Database;
import userPackage.User;

/**
 *
 * @author Pc
 */
public class ScheduledTimeTable {
    private TimeSlot[][] timeSlots;
    private EventTime startTime;
    private EventTime endTime;
    private Day startDay;
    private Day endDay;
    
    private int suggestedDay = -1;
    private int suggestedTime = -1;

    public ScheduledTimeTable(EventTime startTime, EventTime endTime, Day startDay, Day endDay) {
        this.timeSlots = new TimeSlot[Day.numDays][EventTime.numHours];
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDay = startDay;
        this.endDay = endDay;
    }
    
    public void initialiseTimeTable(List<User> usersToMeet) {
        Database db = new Database();
        // for local use only outside college network with putty
        //db.setup("127.0.0.1:3310", "2016_kmon1", "kmon1", "augeheid");
       
        //for use in college network
        db.setup("cs1.ucc.ie:3306", "2016_kmon1", "kmon1", "augeheid");
        
        ResultSet usersEvents = db.select("");
        try {
            while (usersEvents.next()) {
                SimpleDateFormat dateTime = new SimpleDateFormat(DAY_INDEX);
                Date date = usersEvents.getDate("");
                int dayIndex = Integer.parseInt(dateTime.format(date));
                
                dateTime.applyPattern(HOUR_INDEX);
                Time time = usersEvents.getTime("");
                int timeIndex = Integer.parseInt(dateTime.format(time));
                
                int priority = usersEvents.getInt("");
                
                if (timeSlots[dayIndex][timeIndex] == null) {
                    TimeSlot timeSlot = new TimeSlot(time, date);
                    timeSlot.addPriority(priority);
                    timeSlots[dayIndex][timeIndex] = timeSlot;
                } else {
                    timeSlots[dayIndex][timeIndex].addPriority(priority);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error scheduling events");
        }

    }

    public boolean nextSuggestedTimeSlot(int hoursForMeeting, int maxPriority) {
        boolean found = false;
        
        clearPreSuggestedTimeSlot();
        
        int numHours = 0;
        for (int day = (suggestedDay == -1 ? startDay.getIndex() : suggestedDay); 
                numHours != hoursForMeeting && day < endDay.getIndex(); day++) {
            suggestedDay = day;
            for (int time = (suggestedTime == -1 ? startTime.getTimeIndex() : suggestedTime); 
                    numHours != hoursForMeeting && time < endTime.getTimeIndex(); time++) {
                if (timeSlots[day][time].getTotalPriority() <= maxPriority) {
                    suggestedTime = time;
                    numHours++;
                } else {
                    numHours = 0;
                }
            }
        }
        
        for (int time = 0; time < hoursForMeeting; time++) {
            timeSlots[suggestedDay][suggestedTime - time].setSuggested(true);
        }
        
        return found;
    }
    
    private void clearPreSuggestedTimeSlot() {
        boolean found = false;
        if (suggestedDay != -1) {
            for (int time = startTime.getTimeIndex(); !found && time < endTime.getTimeIndex(); time++) {
                if (timeSlots[suggestedDay][time].isSuggested()) {
                    timeSlots[suggestedDay][time].setSuggested(false);
                    found = true;
                }
            }
            found = false;
        }
    }
    
    public String displayTimeTable() {
        String table = "<table>";
        table += createTimeTableHeader(EventTime.getTimes(startTime, endTime));
        for (int day = startDay.getIndex(); day < endDay.getIndex(); day++) {
            table += "</tr>";
            table += "<th>" + Day.convertToDay(day) + "</th>";
            for (int time = startTime.getTimeIndex(); time < endTime.getTimeIndex(); time++) {
                // may need to include if statement to catch null pointer
                table += timeSlots[day][time].printTableCell();
            }
            table += "</tr>";
        }
        table += "</table>";
        
        return table;
    }
    
        /**
     * Creates the header for the timetable
     * Displays the hours during the day
     * 
     * @param hours The list of times to be displayed
     * @return The HTML for the timetable header
     */
    private String createTimeTableHeader(List<EventTime> hours) {
        String header = "<tr><th></th>";
        
        for (EventTime time : hours) {
            // create header converting time to string
            header += "<th>" + time.toString() + "</th>";
        }
        header += "</tr>";
        return header;
    }
}
