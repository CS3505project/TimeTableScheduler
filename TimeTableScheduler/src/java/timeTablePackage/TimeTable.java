/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
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
    private List<Event> events;
    private Map<String, LinkedList<Event>> sortedEvents;

    public TimeTable() {
        events = new ArrayList<Event>();
        sortedEvents = new HashMap<String, LinkedList<Event>>();
    }
    
    public void getUserEvents(String userID) {
        Database db = new Database();
        db.setupFromPropertiesFile("database.properties");
        
        try {
            ResultSet userLectureEvents = db.select("");
            
            while (userLectureEvents.next()) {
                events.add(new Lecture(userLectureEvents.getString(""),
                                       userLectureEvents.getString(""),
                                       userLectureEvents.getInt(""),
                                       userLectureEvents.getTime(""),
                                       userLectureEvents.getString(""),
                                       userLectureEvents.getDate(""),
                                       userLectureEvents.getDate("")));
            }
            
            ResultSet userPracticalEvents = db.select("");
            
            while (userPracticalEvents.next()) {
                events.add(new Practical(userPracticalEvents.getString(""),
                                         userPracticalEvents.getString(""),
                                         userPracticalEvents.getInt(""),
                                         userPracticalEvents.getTime(""),
                                         userPracticalEvents.getString(""),
                                         userPracticalEvents.getDate(""),
                                         userPracticalEvents.getDate("")));
            }
            
            ResultSet userPersonalEvents = db.select("");
            
            while (userPersonalEvents.next()) {
                events.add(new Meeting(userPersonalEvents.getString(""),
                                       userPersonalEvents.getDate(""),
                                       userPersonalEvents.getTime(""),
                                       userPersonalEvents.getString(""),
                                       userPersonalEvents.getString(""),
                                       userPersonalEvents.getInt(""),
                                       (User) userPersonalEvents.getObject("")));
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving user events" + ex.getMessage());
            db.writeLogSQL("Error retrieving users events");
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
    private void sortEvents(List<Day> days, EventTime startTime, EventTime endTime) {
        // initialise the map
        for (Day day : days) {
            sortedEvents.put(day.getDay(), new LinkedList<Event>());
        }
        
        // place events into correct lists while sorting them by time
        for (Event event : events) {
            String day = event.getDayOfWeek();
            if (sortedEvents.containsKey(day)) {
                LinkedList<Event> eventList = sortedEvents.get(day);
                int i = 0;
                while (i < eventList.size()
                        && event.getTime().after(eventList.get(i).getTime())
                        && event.getTime().after(startTime.getTime())
                        && event.getTime().before(endTime.getTime())) {
                    i++;
                }
                eventList.add(i, event);
            }
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
    public String createTimeTable(EventTime startTime, EventTime endTime, 
                                  Day startDay, Day endDay) {
        List<EventTime> hours = EventTime.getTimes(startTime, endTime);
        List<Day> days = Day.getDays(startDay, endDay);
        
        sortEvents(days, startTime, endTime);
        
        String timetable = "<table>";
        timetable += createTimeTableHeader(hours);

        for (Day day : days) {
            timetable += "<tr><th>" + day.getDay() + "</th>";
            int index = 0;
            List<Event> eventsThisDay = sortedEvents.get(day.getDay());
            for (EventTime time : hours) {
                if (index < eventsThisDay.size() && 
                        time.getTime().equals(eventsThisDay.get(index).getTime())) {
                    timetable += "<td " + eventsThisDay.get(index).displayTableHTML() + " >" 
                                 + eventsThisDay.get(index).toString() + "</td>";
                    index++;
                } else {
                    timetable += "<td></td>";
                }
            }
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
            header += "<th>" + time.toString() + "</th>";
        }
        header += "</tr>";
        return header;
    }

    /**
     * Used to test the sorting method by creating fake entries
     * in the timetable
     */
    public void addDummyEvents() {
        events.add(new Meeting("M", Date.valueOf("2015-03-12"), Time.valueOf("12:00:00"), "WGB 1.11"));
        events.add(new Lecture("L", Date.valueOf("2015-03-13"), Time.valueOf("14:00:00"), "WGB G27"));
        events.add(new Practical("P", Date.valueOf("2015-03-11"), Time.valueOf("15:00:00"), "WGB G20"));
        
        events.add(new Meeting("M2", Date.valueOf("2015-03-12"), Time.valueOf("13:00:00"), "WGB 1.07"));
        events.add(new Lecture("L2", Date.valueOf("2015-03-13"), Time.valueOf("16:00:00"), "WGB G21"));
        events.add(new Practical("P2", Date.valueOf("2015-03-11"), Time.valueOf("13:00:00"), "WGB G15"));
        
        events.add(new Meeting("M3", Date.valueOf("2015-03-12"), Time.valueOf("11:00:00"), "WGB 1.15"));
        events.add(new Lecture("L3", Date.valueOf("2015-03-13"), Time.valueOf("10:00:00"), "WGB G22"));
        events.add(new Practical("P3", Date.valueOf("2015-03-11"), Time.valueOf("16:00:00"), "WGB 2.01"));
        
        events.add(new Meeting("M4", Date.valueOf("2015-03-12"), Time.valueOf("09:00:00"), "WGB 1.20"));
        events.add(new Lecture("L4", Date.valueOf("2015-03-13"), Time.valueOf("17:00:00"), "WGB 2.11"));
        events.add(new Practical("P4", Date.valueOf("2015-03-11"), Time.valueOf("14:00:00"), "WGB G25"));
    }

    /**
     * Return a list of events in the timetable
     * 
     * @return List of events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Returns the sorted list of events
     * May return null if sortEvents not called first
     * 
     * @return Map of the events sorted by day and time 
     */
    private Map<String, LinkedList<Event>> getSortedEvents() {
        return sortedEvents;
    }

    /**
     * Filter the results in the timetable
     */
    public void applyFilter() {
        // filter the events in the table
    }

}
