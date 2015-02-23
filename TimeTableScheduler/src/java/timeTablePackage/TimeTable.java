/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import toolsPackage.Database;

/**
 * Represents the users timetable and carries out any actions that may be 
 * performed on the timetable
 * 
 * @author John O Riordan
 */
public class TimeTable {
    private Map<String, LinkedList<Event>> events;
    private EventTime startTime;
    private EventTime endTime;
    private Day startDay;
    private Day endDay;
    
    public static final String DAY_INDEX = "u";
    public static final String HOUR_INDEX = "k";

    public TimeTable(EventTime startTime, EventTime endTime, Day startDay, Day endDay) {
        this.events = new HashMap<String, LinkedList<Event>>();
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDay = startDay;
        this.endDay = endDay;
    }
    
    /**
     * Checks if the event specified by the day and time indexes are conflicting
     * with existing events
     * 
     * @param day Day the event is occurring
     * @param times Times for the event
     * @return True if the event doesn't conflict with existing events.
     */
    public boolean conflictWithEvents(Day day, EventTime[] times) {
        boolean conflict = false;
        
        for (int i = 0; i < times.length && !conflict; i++) {
            for (Event event : events.get(day.getDay())) {
                if (event.getTime().equals(times[i].getTime())) {
                    conflict = true;
                }
            }
        }
        
        return conflict;
    }

    /**
     * Set the time to start displaying from in the timetable
     * 
     * @param startTime The start time
     */
    public void setStartTime(EventTime startTime) {
        this.startTime = startTime;
    }
    
    /**
     * Set the time to stop displaying at in the timetable
     * 
     * @param startTime The end time
     */
    public void setEndTime(EventTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Set the day to start displaying from in the timetable
     * 
     * @param startTime The start day
     */
    public void setStartDay(Day startDay) {
        this.startDay = startDay;
    }

    /**
     * Set the day to stop displaying at in the timetable
     * 
     * @param startTime The end day
     */
    public void setEndDay(Day endDay) {
        this.endDay = endDay;
    }
    
    /**
     * Enters the events that a specific user has into the list of events
     * 
     * @param userID The ID of the user
     */
    public void addUserEvents(String userID) {
        initialiseEventLists();
        
        Database db = Database.getSetupDatabase();
        
        try {
            // retrieve list of lectures for a particular user id
            ResultSet userLectureEvents = db.select("SELECT Lecture.moduleCode, semester, weekday, Lecture.time, room, startDate, endDate " +
                                                    "FROM Lecture " +
                                                    "WHERE moduleCode NOT IN " +
                                                    "(" +
                                                    "	SELECT moduleCode " +
                                                    "	FROM Cancellation " +
                                                    "	WHERE WEEK(Cancellation.date) = WEEK(CURDATE()) " +
                                                    "	AND weekday = WEEKDAY(Cancellation.date + 1) " +
                                                    "	AND Cancellation.time = Lecture.time " +
                                                    ")" +
                                                    "AND Lecture.moduleCode IN " +
                                                    "	(SELECT Lecture.moduleCode " +
                                                    "	FROM ModuleInCourse " +
                                                    "	WHERE courseid = " +
                                                    "		(SELECT courseid " +
                                                    "		FROM GroupTakesCourse " +
                                                    "		WHERE gid IN " +
                                                    "			(SELECT gid " +
                                                    "			FROM InGroup " +
                                                    "			WHERE uid = " + userID + ")));");
            
            while (userLectureEvents.next()) {
                Lecture lecture = new Lecture(userLectureEvents.getString("moduleCode"),
                                            userLectureEvents.getString("semester"),
                                            userLectureEvents.getInt("weekday"),
                                            userLectureEvents.getTime("time"),
                                            userLectureEvents.getString("room"),
                                            userLectureEvents.getDate("startDate"),
                                            userLectureEvents.getDate("endDate"));
                addEventToList(lecture);
            }

            // retrieve list of practicals for a particular user id
            ResultSet userPracticalEvents = db.select("SELECT Practical.moduleCode, semester, weekday, Practical.time, room, startDate, endDate " +
                                                        "FROM Practical " +
                                                        "WHERE moduleCode NOT IN " +
                                                        "(" +
                                                        "	SELECT moduleCode " +
                                                        "	FROM Cancellation " +
                                                        "	WHERE WEEK(Cancellation.date) = WEEK(CURDATE()) " +
                                                        "	AND weekday = WEEKDAY(Cancellation.date + 1) " +
                                                        "	AND Cancellation.time = Practical.time " +
                                                        ")" +
                                                        "AND Practical.moduleCode IN " +
                                                        "	(SELECT Practical.moduleCode " +
                                                        "	FROM ModuleInCourse " +
                                                        "	WHERE courseid = " +
                                                        "		(SELECT courseid " +
                                                        "		FROM GroupTakesCourse " +
                                                        "		WHERE gid IN " +
                                                        "			(SELECT gid " +
                                                        "			FROM InGroup " +
                                                        "			WHERE uid = " + userID + ")));");
                    
            while (userPracticalEvents.next()) {
                Practical practical = new Practical(userPracticalEvents.getString("moduleCode"),
                                                    userPracticalEvents.getString("semester"),
                                                    userPracticalEvents.getInt("weekday"),
                                                    userPracticalEvents.getTime("time"),
                                                    userPracticalEvents.getString("room"),
                                                    userPracticalEvents.getDate("startDate"),
                                                    userPracticalEvents.getDate("endDate"));
                addEventToList(practical);
            }
            
            // retrieve list of meetings that a particular user id is involved with
            ResultSet userPersonalEvents = db.select("SELECT meetingid, date, time, room, description, priority, organiser_uid " +
                                                    "FROM Meeting " +
                                                    "WHERE meetingid = " +
                                                    "(	SELECT mid " +
                                                    "	FROM HasMeeting " +
                                                    "	WHERE uid = " + userID + ");");
                                                                    
            while (userPersonalEvents.next()) {
                Meeting meeting = new Meeting(userPersonalEvents.getString("meetingid"),
                                            userPersonalEvents.getDate("date"),
                                            userPersonalEvents.getTime("time"),
                                            userPersonalEvents.getString("room"),
                                            userPersonalEvents.getString("description"),
                                            userPersonalEvents.getInt("priority"),
                                            userPersonalEvents.getString("organiser_uid"));
                addEventToList(meeting);
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving user events" + ex.getMessage());
            db.writeLogSQL("Error retrieving users events");
        } 
        
        db.close();
    }
    
    /**
     * Inserts the event into the list of already sorted events.
     * 
     * @param event Event to be added
     */
    private void addEventToList(Event event) {
        events.get(event.getDayOfWeek()).set(event.getHourIndex(), event);
    }
    
    /**
     * Initialises the map of lists of events for each day of the week
     */
    private void initialiseEventLists() {
        for (Day day : Day.getDays(startDay, endDay)) {
            LinkedList<Event> events = new LinkedList<Event>();
            for (int timeIndex = 0; timeIndex <= EventTime.numHours; timeIndex++) {
                events.add(null);
            }
            this.events.put(day.getDay(), events);
        }
    }

    /**
     * Generates a timetable from HTML with all the events listed
     * in order of day and time
     * 
     * @param startTime The time to start displaying from in the table (exclusive)
     * @param endTime The time to stop displaying at in the table (exclusive)
     * @param startDay The day to start displaying from in the table (inclusive)
     * @param endDay The day to stop displaying at in the table (inclusive)
     * @return Timetable as HTML code 
     */
    public String createTimeTable(EventType filterEvent) {
        List<EventTime> hours = EventTime.getTimes(startTime, endTime);
        
        List<Day> days = Day.getDays(startDay, endDay);
        
        //     sortEvents();
        //create table
        String timetable = "<table>";
        //create header for timetable
        timetable += createTimeTableHeader(hours);
        // loop through days
        for (Day day : days) {
            //generate days of time table
            timetable += "<tr><th>" + day.getDay() + "</th>";
            // store events of the day
            List<Event> scheduledEvents = events.get(day.getDay());
            for (EventTime time : hours) { 
                Event curEvent = scheduledEvents.get(time.getTimeIndex());
                if (curEvent != null 
                        && filterEvent(curEvent.getEventType(), filterEvent)
                        && time.getTime().equals(curEvent.getTime())) {
                    //puts event into correct time slot 
                    timetable += curEvent.displayTableHTML();

                } else {
                    timetable += "<td></td>";
                }
            }
            //end tags 
            timetable += "</tr>";
        }
        timetable += "<caption>Week 4, 23/23/1234 to 12/12/1234</caption>";
        timetable += "</table>";
        
        return timetable;
    }
    
    private boolean filterEvent(EventType eventType, EventType filterType) {
        return (eventType.equals(filterType) || filterType.equals(EventType.ALL_EVENTS));
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

    /**
     * Returns the sorted list of events
     * May return null if sortEvents not called first
     * 
     * @return Map of the events sorted by day and time 
     */
    public Map<String, LinkedList<Event>> getSortedEvents() {
        return events;
    }

    /**
     * Filter the results in the timetable
     */
    public void applyFilter() {
        // filter the events in the table
    }

}
