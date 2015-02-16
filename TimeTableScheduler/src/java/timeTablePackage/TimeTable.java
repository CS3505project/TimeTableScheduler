/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import toolsPackage.Database;
import userPackage.User;

/**
 * Represents the users timetable and carries out any actions that may be 
 * performed on the timetable
 * 
 * @author John O Riordan
 */
public class TimeTable {
    private Map<String, LinkedList<Event>> sortedEvents;
    private EventTime startTime;
    private EventTime endTime;
    private Day startDay;
    private Day endDay;
    
    public static final String DAY_INDEX = "u";
    public static final String HOUR_INDEX = "k";

    public TimeTable(EventTime startTime, EventTime endTime, Day startDay, Day endDay) {
        this.sortedEvents = new HashMap<String, LinkedList<Event>>();
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDay = startDay;
        this.endDay = endDay;
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
        
        Database db = new Database();
        // for local use only outside college network with putty
        //db.setup("127.0.0.1:3310", "2016_kmon1", "kmon1", "augeheid");
        //db.setup("127.0.0.1:3310", "2016_dol8", "dol8", "zahriexo");
       
        //for use in college network
        db.setup("cs1.ucc.ie:3306", "2016_kmon1", "kmon1", "augeheid");
        
        try {
            // retrieve list of lectures for a particular user id
            ResultSet userLectureEvents = db.select("SELECT Lecture.moduleCode, semester, weekday, Lecture.time, room, startDate, endDate " +
                                                    "FROM Lecture JOIN Cancellation " +
                                                    "ON Lecture.moduleCode = Cancellation.moduleCode " +
                                                    "WHERE CURDATE() BETWEEN startDate AND endDate " +
                                                    "AND WEEK(Cancellation.date) != WEEK(CURDATE()) " +
                                                    "OR weekday != WEEKDAY(Cancellation.date + 1) " +
                                                    "OR Cancellation.time != Lecture.time " +
                                                    "AND Lecture.moduleCode IN " +
                                                            "(SELECT Lecture.moduleCode " +
                                                            "FROM ModuleInCourse " +
                                                            "WHERE courseid = " +
                                                                    "(SELECT courseid " +
                                                                    "FROM GroupTakesCourse " +
                                                                    "WHERE gid IN " +
                                                                            "(SELECT gid " +
                                                                            "FROM InGroup " +
                                                                            "WHERE uid = " + userID + ")));");
            
            while (userLectureEvents.next()) {
                Lecture lecture = new Lecture(userLectureEvents.getString("moduleCode"),
                                            userLectureEvents.getString("semester"),
                                            userLectureEvents.getInt("weekday"),
                                            userLectureEvents.getTime("time"),
                                            userLectureEvents.getString("room"),
                                            userLectureEvents.getDate("startDate"),
                                            userLectureEvents.getDate("endDate"));
                addEventToSortedList(lecture);
            }

            // retrieve list of practicals for a particular user id
            ResultSet userPracticalEvents = db.select("SELECT Practical.moduleCode, semester, weekday, Practical.time, room, startDate, endDate " +
                                                    "FROM Practical JOIN Cancellation " +
                                                    "ON Practical.moduleCode = Cancellation.moduleCode " +
                                                    "WHERE CURDATE() BETWEEN startDate AND endDate " +
                                                    "AND WEEK(Cancellation.date) != WEEK(CURDATE()) " +
                                                    "OR weekday != WEEKDAY(Cancellation.date + 1) " +
                                                    "OR Cancellation.time != Practical.time " +
                                                    "AND Practical.moduleCode IN " +
                                                            "(SELECT Practical.moduleCode " +
                                                            "FROM ModuleInCourse " +
                                                            "WHERE courseid = " +
                                                                    "(SELECT courseid " +
                                                                    "FROM GroupTakesCourse " +
                                                                    "WHERE gid IN " +
                                                                            "(SELECT gid " +
                                                                            "FROM InGroup " +
                                                                            "WHERE uid = " + userID + ")));");
                    
            while (userPracticalEvents.next()) {
                Practical practical = new Practical(userPracticalEvents.getString("moduleCode"),
                                                    userPracticalEvents.getString("semester"),
                                                    userPracticalEvents.getInt("weekday"),
                                                    userPracticalEvents.getTime("time"),
                                                    userPracticalEvents.getString("room"),
                                                    userPracticalEvents.getDate("startDate"),
                                                    userPracticalEvents.getDate("endDate"));
                addEventToSortedList(practical);
            }
            
            // retrieve list of meetings that a particular user id is involved with
            ResultSet userPersonalEvents = db.select("SELECT meetingid, date, time, room, description, priority, organiser_uid " +
                                                    "FROM Meeting " +
                                                    "WHERE WEEK(date) = WEEK(CURDATE()) " +
                                                    "AND meetingid = " +
                                                    "(SELECT mid " +
                                                            "FROM HasMeeting " +
                                                            "WHERE uid = " + userID + ");");
                                                                    
            while (userPersonalEvents.next()) {
                Meeting meeting = new Meeting(userPersonalEvents.getString("meetingid"),
                                            userPersonalEvents.getDate("date"),
                                            userPersonalEvents.getTime("time"),
                                            userPersonalEvents.getString("room"),
                                            userPersonalEvents.getString("description"),
                                            userPersonalEvents.getInt("priority"),
                                            userPersonalEvents.getString("organiser_uid"));
                addEventToSortedList(meeting);
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
    private void addEventToSortedList(Event event) {
        LinkedList<Event> eventList = sortedEvents.get(event.getDayOfWeek());
        int i = 0;
        while (i < eventList.size()
                && event.getTime().before(eventList.get(i).getTime())) {
            i++;
        }
        
        if (event.getTime().after(startTime.getTime())
                && event.getTime().before(endTime.getTime())) {
            eventList.add(i, event);
        }
    }
    
    /**
     * Initialises the map of lists of events for each day of the week
     */
    private void initialiseEventLists() {
        for (Day day : Day.getDays(startDay, endDay)) {
            sortedEvents.put(day.getDay(), new LinkedList<Event>());
        }
    }



    /**
     * Sort the events by day and time.
     * Resulting sorted events are placed in the map as lists
     * corresponding to the day they are scheduled on.
     * 
     * @param days List of days to that need to be sorted
     * @param startTime The starting time for events to be considered for sorting
     * @param endTime The finishing time for events to be considered for sorting
     */
    /*private void sortEvents() {
        // initialise the map
        for (Day day : Day.getDays(startDay, endDay)) {
            sortedEvents.put(day.getDay(), new LinkedList<Event>());
        }
        
        // place events into correct lists while sorting them by time
        for (Event event : events) {
            String day = event.getDayOfWeek();
            if (sortedEvents.containsKey(day)) {
                LinkedList<Event> eventList = sortedEvents.get(day);
                int day = 0;
                while (day < eventList.size()
                        && event.getTime().after(eventList.get(day).getTime())
                        && event.getTime().after(startTime.getTime())
                        && event.getTime().before(endTime.getTime())) {
                    day++;
                }
                eventList.add(day, event);
            }
        }
    }
    */

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
    public String createTimeTable() {
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
            int index = 0;
            // store events of the day
            List<Event> eventsThisDay = sortedEvents.get(day.getDay());
            for (EventTime time : hours) {  
                if (index < eventsThisDay.size() && 
                        time.getTime().equals(eventsThisDay.get(index).getTime())) {
                    //puts event into correct time slot 
                    timetable += "<td " + eventsThisDay.get(index).displayTableHTML() + " >" 
                                 + eventsThisDay.get(index).toString() + "</td>";
                    index++;
                   
                } else {
                    timetable += "<td></td>";
                }
            }
            //end tags 
            timetable += "</tr>";
        }
        timetable += "</table>";
        
        return timetable;
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
        return sortedEvents;
    }

    /**
     * Filter the results in the timetable
     */
    public void applyFilter() {
        // filter the events in the table
    }

}
