/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;
import static timeTablePackage.TimeTable.HOUR_INDEX;
import toolsPackage.Database;

/**
 * Timetable that represents the combined timetables of all users involved
 * in the scheduled meeting
 * 
 * @author John O Riordan
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
    
    /**
     * Setups up the timetable by entering all events already scheduled for
     * the list of users
     * 
     * @param usersToMeet User in the meeting
     */
    public void initialiseTimeTable(String[] usersToMeet) {
        String sqlUserList = convertToSqlList(usersToMeet);
        Database db = Database.getSetupDatabase();
        
        // get all events for each user in meeting
        ResultSet usersEvents = db.select("SELECT weekday, Practical.time, 4 'priority' " +
                                        "FROM Practical " +
                                        "WHERE moduleCode NOT IN " +
                                        "( " +
                                        "	SELECT moduleCode " +
                                        "	FROM Cancellation " +
                                        "	WHERE WEEK(Cancellation.date) = WEEK(CURDATE()) " +
                                        "	AND weekday = WEEKDAY(Cancellation.date + 1) " +
                                        "	AND Cancellation.time = Practical.time " +
                                        ") " +
                                        "AND Practical.moduleCode IN " +
                                        "	(SELECT Practical.moduleCode " +
                                        "	FROM ModuleInCourse " +
                                        "	WHERE courseid IN " +
                                        "		(SELECT courseid " +
                                        "		FROM GroupTakesCourse " +
                                        "		WHERE gid IN " +
                                        "			(SELECT gid " +
                                        "			FROM InGroup " +
                                        "			WHERE uid IN " + sqlUserList + "))) " +
                                        "UNION " +
                                        "SELECT weekday, Lecture.time, 5 'priority' " +
                                        "FROM Lecture " +
                                        "WHERE moduleCode NOT IN " +
                                        "( " +
                                        "	SELECT moduleCode " +
                                        "	FROM Cancellation " +
                                        "	WHERE WEEK(Cancellation.date) = WEEK(CURDATE()) " +
                                        "	AND weekday = WEEKDAY(Cancellation.date + 1) " +
                                        "	AND Cancellation.time = Lecture.time " +
                                        ") " +
                                        "AND Lecture.moduleCode IN " +
                                        "	(SELECT Lecture.moduleCode " +
                                        "	FROM ModuleInCourse " +
                                        "	WHERE courseid IN " +
                                        "		(SELECT courseid " +
                                        "		FROM GroupTakesCourse " +
                                        "		WHERE gid IN " +
                                        "			(SELECT gid " +
                                        "			FROM InGroup " +
                                        "			WHERE uid IN " + sqlUserList + "))) " +
                                        "UNION " +
                                        "SELECT WEEKDAY(date + 1) as 'weekday', time, priority " +
                                        "FROM Meeting " +
                                        "WHERE meetingid IN " +
                                        "(	SELECT mid " +
                                        "	FROM HasMeeting " +
                                        "	WHERE uid IN " + sqlUserList + ");");
        try {            
            while (usersEvents.next()) {
                int dayIndex = usersEvents.getInt("weekday");
                
                SimpleDateFormat dateTime = new SimpleDateFormat(HOUR_INDEX);
                Time time = usersEvents.getTime("time");
                int timeIndex = Integer.parseInt(dateTime.format(time));
                                
                int priority = usersEvents.getInt("priority");
                                
                // create a timeslot object for every event or it updates 
                // an existing an existing timeslot
                if (timeSlots[dayIndex][timeIndex] == null) {
                    TimeSlot timeSlot = new TimeSlot(dayIndex, timeIndex);
                    timeSlot.addPriority(priority);
                    timeSlots[dayIndex][timeIndex] = timeSlot;
                } else {
                    timeSlots[dayIndex][timeIndex].addPriority(priority);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error scheduling events");
        }
        
        db.close();
        
        // fill in the rest of the timeslots with blanks
        for (int dayIndex = 0; dayIndex < Day.numDays; dayIndex++) {
            for (int timeIndex = 0; timeIndex < EventTime.numHours; timeIndex++) {
                if (timeSlots[dayIndex][timeIndex] == null) {
                    timeSlots[dayIndex][timeIndex] = new TimeSlot();
                }
            }
        }
    }
    
    /**
     * Creates a list of user IDs to be used in SQL queries
     * 
     * @param userIDs list of user IDs
     * @return SQL styled list of user IDs
     */
    private static String convertToSqlList(String[]items) {
        String sql = "(";
        for (int i = 0; i < items.length; i++) {
            sql += "\"" + items[i] + "\"" + (i < items.length - 1 ? ", " : "");
        }
        sql += ")";
        return sql;
    }

    /**
     * Highlight the next suggested timeslot(s) for the meeting being organised
     * 
     * @param hoursForMeeting Duration of the meeting
     * @param maxPriority Max priority that can be scheduled over
     * @param clearPrevious Remove the most recently suggested timeslot
     * @return True if another timeslot exists
     */
    public boolean nextSuggestedTimeSlot(int hoursForMeeting, int maxPriority, boolean clearPrevious) {        
        
        if (clearPrevious) {
            clearPreSuggestedTimeSlot();
        }
        
        boolean found = false;
        int numHours = 0;
        // loop through each timeslot in the timetable
        for (int day = (suggestedDay == -1 ? startDay.getIndex() : suggestedDay); 
                numHours != hoursForMeeting && day < endDay.getIndex(); day++) {
            suggestedDay = day;
            for (int time = (suggestedTime == -1 ? startTime.getTimeIndex() : suggestedTime); 
                    numHours != hoursForMeeting && time < endTime.getTimeIndex(); time++) {
                if (timeSlots[day][time].getTotalPriority() <= maxPriority) {
                    suggestedTime = time;
                    numHours++;
                    found = true;
                } else {
                    numHours = 0;
                    found = false;
                }
            }
        }
        
        if (found) {
            for (int time = 0; time < hoursForMeeting; time++) {
                timeSlots[suggestedDay][suggestedTime - time].setSuggested(true);
            }
        }
        
        return found;
    }
    
    /**
     * Clears the most recently suggested time slots in the timetable
     */
    private void clearPreSuggestedTimeSlot() {
        if (suggestedDay != -1) {
            for (int time = startTime.getTimeIndex(); time < endTime.getTimeIndex(); time++) {
                if (timeSlots[suggestedDay][time].isSuggested()) {
                    timeSlots[suggestedDay][time].setSuggested(false);
                }
            }
        }
    }
    
    /**
     * Clears all suggested time slots in the timetable
     */
    public void clearAllSuggestedTimeSlot() {
        for (int day = startDay.getIndex(); day < endDay.getIndex(); day ++) {
            for (int time = startTime.getTimeIndex(); time < endTime.getTimeIndex(); time++) {
                if (timeSlots[day][time].isSuggested()) {
                    timeSlots[day][time].setSuggested(false);
                }
            }
        }
    }
    
    /**
     * Checks if the event specified by the day and time indexes are conflicting
     * with existing events those priority greater than the max priority
     * 
     * @param dayIndex The index of the day the event is occurring
     * @param timeIndexes Array of time indexes for the duration of the event
     * @param maxPriority Max priority that can be scheduled over
     * @return True if the event doesn't conflict with existing events.
     */
    public boolean conflictWithEvents(int dayIndex, int[] timeIndexes, int maxPriority) {
        boolean result = false;
        
        for (int time = 0; time < timeIndexes.length; time++) {
            if (timeSlots[dayIndex][time].getTotalPriority() > maxPriority) {
                result = true;
            }
        }
        
        return result;
    }
    
    /**
     * Creates the HTML needed to display the timetable with any suggested slots
     * 
     * @return HTML to display timetable
     */
    public String displayTimeTable() {
        String table = "<table>";
        List<EventTime> hours = EventTime.getTimes(startTime, endTime);
        table += createTimeTableHeader(hours);
        for (int day = startDay.getIndex(); day <= endDay.getIndex(); day++) {
            table += "<tr>";
            table += "<th>" + Day.convertToDay(day) + "</th>";
            for (EventTime time : hours) {
                table += timeSlots[day][time.getTimeIndex()].printTableCell();
            }
            table += "</tr>";
        }
        table += "<caption>Week 4, 23/23/1234 to 12/12/1234</caption>";
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
