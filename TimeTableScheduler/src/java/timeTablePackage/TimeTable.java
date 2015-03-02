/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import toolsPackage.Database;

/**
 * Represents a timetable and carries out any actions that may be 
 * performed on the timetable
 * 
 * @author John O Riordan
 */
public class TimeTable {
    private TimeSlot[][] events;
    private EventTime startTime;
    private EventTime endTime;
    private Day startDay;
    private Day endDay;
    
    private Calendar calendar = Calendar.getInstance(Locale.FRANCE);
    private String displayDate;
    
    private int suggestedDay = -1;
    private int suggestedTime = -1;
    
    public static final String HOUR_INDEX = "k";
    public static final String DAY = "EEEE";

    public TimeTable(EventTime startTime, EventTime endTime, Day startDay, Day endDay) {
        this.events = new TimeSlot[Day.numDays][EventTime.numHours];
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDay = startDay;
        this.endDay = endDay;
    }
    
    public static TimeTable getPreSetTimeTable() {
        return new TimeTable(EventTime.NINE, EventTime.SEVENTEEN, Day.MONDAY, Day.FRIDAY);
    }
    
    public void setDisplayWeek(String displayDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (displayDate != null) {
                dateFormat.parse(displayDate);
                Date date = dateFormat.parse(displayDate);
                calendar.setTime(date);
            } else {
                System.err.println("Setting to current date");
                calendar.setTime(new Date());
            }
        } catch (ParseException ex) {
            System.err.println("Setting to current date");
            calendar.setTime(new Date());
        } finally {
            this.displayDate = dateFormat.format(calendar.getTime());
        }
    }
    
    public Calendar getDisplayWeek() {
        return calendar;
    }
    
    /**
     * Checks if the event specified by the day and time indexes are conflicting
     * with existing events
     * 
     * @param day Day the event is occurring
     * @param times Times for the event
     * @return True if the event doesn't conflict with existing events.
     */
    public boolean conflictWithEvents(Date date, Time time, int duration) {
        boolean conflict = false;
        
        // Print dates of the current week starting on Monday
        SimpleDateFormat format = new SimpleDateFormat(HOUR_INDEX);
        
        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        // Set the calendar to monday of the current week
        cal.setTime(time);
        int hour = Integer.parseInt(format.format(cal.getTime()));
        
        format.applyPattern(DAY);
        int day = Day.convertToDay(format.format(date)).getIndex();
        for (int i = 0; i < duration; i++) {
            if (events[day][hour + i].hasEvents()) {
                conflict = true;
            }
        }
        
        return conflict;
    }
    
    /**
     * Checks if the event specified by the day and time indexes are conflicting
     * with existing events whose priority is greater than the max priority
     * 
     * @param dayIndex The index of the day the event is occurring
     * @param timeIndexes Array of time indexes for the duration of the event
     * @param maxPriority Max priority that can be scheduled over
     * @return True if the event doesn't conflict with existing events.
     */
    public boolean conflictWithEvents(Date date, Time time, int duration, int maxPriority) {
        boolean conflict = false;
        
        // Print dates of the current week starting on Monday
        SimpleDateFormat format = new SimpleDateFormat(HOUR_INDEX);
        
        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        // Set the calendar to monday of the current week
        cal.setTime(time);
        int hour = Integer.parseInt(format.format(cal.getTime()));
        
        format.applyPattern(DAY);
        int day = Day.convertToDay(format.format(date)).getIndex();

        for (int i = 0; i < duration && !conflict; i++) {
            System.out.println("event" + events[day][hour + i].getTotalPriority());
            System.out.println("arg" + maxPriority);
            if (events[day][hour + i].getTotalPriority() >= maxPriority) {
                conflict = true;
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
    
    public void setEndTime(EventTime endTime) {
        this.endTime = endTime;
    }

    public void setStartDay(Day startDay) {
        this.startDay = startDay;
    }

    public void setEndDay(Day endDay) {
        this.endDay = endDay;
    }
    
    /**
     * Setups up the timetable by entering all events already scheduled for
     * the list of users
     * 
     * @param usersToMeet User in the meeting
     */
    public void initialiseTimeTable(List<String> usersToMeet) {
        String sqlUserList = convertToSqlList(usersToMeet);
        Database db = Database.getSetupDatabase();
                
        // get all events for each user in meeting
        ResultSet usersEvents = db.select("SELECT weekday, Practical.time, " + EventPriority.PRACTICAL.getPriority() + " 'priority' " +
                                        "FROM Practical " +
                                        "WHERE moduleCode NOT IN " +
                                        "( " +
                                        "	SELECT moduleCode " +
                                        "	FROM Cancellation " +
                                        "	WHERE WEEK(Cancellation.date) = WEEK(\"" + displayDate + "\") " +
                                        "	AND weekday = WEEKDAY(Cancellation.date) " +
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
                                        "SELECT weekday, Lecture.time, " + EventPriority.LECTURE.getPriority() + " 'priority' " +
                                        "FROM Lecture " +
                                        "WHERE moduleCode NOT IN " +
                                        "( " +
                                        "	SELECT moduleCode " +
                                        "	FROM Cancellation " +
                                        "	WHERE WEEK(Cancellation.date) = WEEK(\"" + displayDate + "\") " +
                                        "	AND weekday = WEEKDAY(Cancellation.date) " +
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
                                        "SELECT WEEKDAY(date) as 'weekday', time, priority " +
                                        "FROM Meeting " +
                                        "WHERE meetingid IN " +
                                        "(	SELECT mid " +
                                        "	FROM HasMeeting " +
                                        "	WHERE uid IN " + sqlUserList + ") " +
                                        "AND WEEK(date) = WEEK(\"" + displayDate + "\");");
        try {            
            while (usersEvents.next()) {
                int dayIndex = usersEvents.getInt("weekday");
                
                SimpleDateFormat dateTime = new SimpleDateFormat(HOUR_INDEX);
                Time time = usersEvents.getTime("time");
                int timeIndex = Integer.parseInt(dateTime.format(time));
                                
                int priority = usersEvents.getInt("priority");
                                
                addEvent(priority, dayIndex, timeIndex);
            }
        } catch (SQLException ex) {
            System.err.println("Error scheduling events");
        }
        
        db.close();
    }
    
    /**
     * Creates a list of user IDs to be used in SQL queries
     * 
     * @param userIDs list of user IDs
     * @return SQL styled list of user IDs
     */
    private static String convertToSqlList(List<String> items) {
        String sql = "(";
        for (int i = 0; i < items.size(); i++) {
            sql += "\"" + items.get(i) + "\"" + (i < items.size() - 1 ? ", " : "");
        }
        sql += ")";
        return sql;
    }
    
    /**
     * Enters the events that a specific user has into the list of events
     * 
     * @param userID The ID of the user
     */
    public void initialiseTimeTable(String userID) {        
        Database db = Database.getSetupDatabase();
        
        try {
            // retrieve list of lectures for a particular user id
            ResultSet userLectureEvents = db.select("SELECT Lecture.moduleCode, semester, weekday, Lecture.time, room, startDate, endDate " +
                                                    "FROM Lecture " +
                                                    "WHERE moduleCode NOT IN " +
                                                    "(" +
                                                    "	SELECT moduleCode " +
                                                    "	FROM Cancellation " +
                                                    "	WHERE WEEK(Cancellation.date) = WEEK(\"" + displayDate + "\") " +
                                                    "	AND weekday = WEEKDAY(Cancellation.date) " +
                                                    "	AND Cancellation.time = Lecture.time " +
                                                    ")" +
                                                    "AND Lecture.moduleCode IN " +
                                                    "	(SELECT Lecture.moduleCode " +
                                                    "	FROM ModuleInCourse " +
                                                    "	WHERE courseid IN " +
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
                addEvent(lecture);
            }

            // retrieve list of practicals for a particular user id
            ResultSet userPracticalEvents = db.select("SELECT Practical.moduleCode, semester, weekday, Practical.time, room, startDate, endDate " +
                                                        "FROM Practical " +
                                                        "WHERE moduleCode NOT IN " +
                                                        "(" +
                                                        "	SELECT moduleCode " +
                                                        "	FROM Cancellation " +
                                                        "	WHERE WEEK(Cancellation.date) = WEEK(\"" + displayDate + "\") " +
                                                        "	AND weekday = WEEKDAY(Cancellation.date) " +
                                                        "	AND Cancellation.time = Practical.time " +
                                                        ")" +
                                                        "AND Practical.moduleCode IN " +
                                                        "	(SELECT Practical.moduleCode " +
                                                        "	FROM ModuleInCourse " +
                                                        "	WHERE courseid IN " +
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
                addEvent(practical);
            }
            
            // retrieve list of meetings that a particular user id is involved with
            ResultSet userPersonalEvents = db.select("SELECT meetingid, date, time, room, description, priority, organiser_uid " +
                                                    "FROM Meeting " +
                                                    "WHERE meetingid IN " +
                                                    "(	SELECT mid " +
                                                    "	FROM HasMeeting " +
                                                    "	WHERE uid = " + userID + ") " +
                                                    "AND WEEK(date) = WEEK(\"" + displayDate + "\");");
                                                                    
            while (userPersonalEvents.next()) {
                Meeting meeting = new Meeting(userPersonalEvents.getString("meetingid"),
                                            userPersonalEvents.getDate("date"),
                                            userPersonalEvents.getTime("time"),
                                            userPersonalEvents.getString("room"),
                                            userPersonalEvents.getString("description"),
                                            userPersonalEvents.getInt("priority"),
                                            userPersonalEvents.getString("organiser_uid"));
                addEvent(meeting);
            }
        } catch (SQLException ex) {
            System.err.println("Error retrieving user events" + ex.getMessage());
            db.writeLogSQL("Error retrieving users events");
        } 
        
        db.close();
    }
    
    /**
     * Set ups the timeslot matrix to hold empty TimeSlot objects
     */
    public void setupTimeSlots() {  

        // Print dates of the current week starting on Monday
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                
        for (Day day : Day.values()) {
            for (EventTime time : EventTime.values()) {
                events[day.getIndex()][time.getTimeIndex()] 
                        = new TimeSlot(dateFormat.format(calendar.getTime()), time.printSQLTimeFormat());
            }
            calendar.add(Calendar.DATE, 1);
        }
        calendar.add(Calendar.DATE, -Day.numDays);
    }
    
    /**
     * Inserts the event into the list of already sorted events.
     * 
     * @param event Event to be added
     */
    private void addEvent(Event event) {
        events[event.getDayOfWeek()][event.getHourIndex()].addEvent(event);
    }
    
    public List<Event> getEvents(int dayIndex, int timeIndex) {
        return events[dayIndex][timeIndex].getEvents();
    }
    
    /**
     * Inserts a priority into the timeslots.
     * 
     * @param priority Priority to be added
     * @param dayIndex Index of the day for the timeslot
     * @param timeIndex Index of the time for the timeslot
     */
    private void addEvent(int priority, int dayIndex, int timeIndex) {
        events[dayIndex][timeIndex].addPriority(priority);
    }

    /**
     * Generates a timetable from HTML with all the events listed
     * in order of day and time
     * N.B. Set the hideDetail parameter to true when scheduling an event with
     * other users to display the timetable correctly.
     * 
     * @param filterEvent The events to be displayed
     * @param hideDetail Hide the detail when displaying the timeslot
     * @return Timetable as HTML code 
     */
    public String createTimeTable(EventType filterEvent, boolean hideDetail, boolean clickable) {
        List<EventTime> hours = EventTime.getTimes(startTime, endTime);
        List<Day> days = Day.getDays(startDay, endDay);
        
        //create table
        String timetable = "<table>";
        //create header for timetable
        timetable += createTimeTableHeader(hours);
        // loop through days
        for (Day day : days) {
            //generate days of time table
            timetable += "<tr><th>" + day.getDay() + "</th>";
            for (EventTime time : hours) { 
                if (hideDetail) {
                    timetable += events[day.getIndex()][time.getTimeIndex()].printTableCell();
                }
                else {
                    timetable += events[day.getIndex()][time.getTimeIndex()].printDetailedTableCell(filterEvent, clickable);
                }
            }
            //end tags 
            timetable += "</tr>";
        }
        
        // Print dates of the current week starting on Monday
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String startDate = "";
        String endDate = "";

        startDate = df.format(calendar.getTime());
        calendar.add(Calendar.DATE, 6);
        endDate = df.format(calendar.getTime());
        calendar.add(Calendar.DATE, -6);

        timetable += "<caption>" + startDate + " to " + endDate + "</caption>";
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
     * Highlight the next suggested timeslot(s) for the meeting being organised
     * 
     * @param meetingDuration Duration of the meeting
     * @param maxPriority Max priority that can be scheduled over
     * @param clearPrevious Remove the most recently suggested timeslot
     * @return True if another timeslot exists
     */
    public boolean nextSuggestedTimeSlot(int meetingDuration, int maxPriority, boolean clearPrevious) {        
        
        if (clearPrevious) {
            clearSuggestedTimeSlots();
        }
                
        // if reached end reset suggestion to start
        if (suggestedDay == endDay.getIndex()) {
            suggestedDay = -1;
        }
        if (suggestedTime == endTime.getTimeIndex()) {
            suggestedTime = -1;
        }
        
        boolean found = false;
        // loop through each timeslot in the timetable
        for (int day = (suggestedDay == -1 ? startDay.getIndex() : suggestedDay); 
                !found && day < endDay.getIndex(); day++) {
            suggestedDay = day;
            for (int time = (suggestedTime == -1 ? startTime.getTimeIndex() : suggestedTime); 
                    !found && time < endTime.getTimeIndex(); time++) {
                suggestedTime = time;
                found = true;
                for (int i = 0; i < meetingDuration && (time+ i) < endTime.getTimeIndex(); i++) {
                    found = found && (events[day][time + i].getTotalPriority() <= maxPriority);
                }
            }
        }
        
        if (found) {
            highlightSuggestions(meetingDuration);
        }
        
        return found;
    }
    
    /**
     * Clears all suggested time slots in the timetable
     */
    public void clearSuggestedTimeSlots() {
        for (int day = (suggestedDay == -1 ? startDay.getIndex() : suggestedDay); 
                day < endDay.getIndex(); day++) {
            for (int time = (suggestedTime == -1 ? startTime.getTimeIndex() : suggestedTime); 
                    time < endTime.getTimeIndex(); time++) {
                if (events[day][time].isSuggested()) {
                    events[day][time].setSuggested(false);
                }
            }
        }
    }
    
    /**
     * Cycles backwards through the timeslots starting at the suggested time for
     * the length of the meeting
     * 
     * @param meetingDuration The duration of the meeting
     */
    private void highlightSuggestions(int meetingDuration) {
        for (int time = 0; time < meetingDuration; time++) {
            events[suggestedDay][suggestedTime + time].setSuggested(true);
        }
    }

}
