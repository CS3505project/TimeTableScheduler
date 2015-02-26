/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Represents the times the system can display in the timetable
 * 
 * @author John O Riordan
 */
public enum EventTime {
    ZERO("00:00", Time.valueOf("00:00:00"), 0),
    ONE("1:00", Time.valueOf("01:00:00"), 1),
    TWO("2:00", Time.valueOf("02:00:00"), 2),
    THREE("3:00", Time.valueOf("03:00:00"), 3),
    FOUR("4:00", Time.valueOf("04:00:00"), 4),
    FIVE("5:00", Time.valueOf("05:00:00"), 5),
    SIX("6:00", Time.valueOf("06:00:00"), 6),
    SEVEN("7:00", Time.valueOf("07:00:00"), 7),
    EIGHT("8:00", Time.valueOf("08:00:00"), 8),
    NINE("9:00", Time.valueOf("09:00:00"), 9),
    TEN("10:00", Time.valueOf("10:00:00"), 10),
    ELEVEN("11:00", Time.valueOf("11:00:00"), 11),
    TWELVE("12:00", Time.valueOf("12:00:00"), 12),
    THIRTEEN("13:00", Time.valueOf("13:00:00"), 13),
    FOURTEEN("14:00", Time.valueOf("14:00:00"), 14),
    FIFTHTEEN("15:00", Time.valueOf("15:00:00"), 15),
    SIXTEEN("16:00", Time.valueOf("16:00:00"), 16),
    SEVENTEEN("17:00", Time.valueOf("17:00:00"), 17),
    EIGHTEEN("18:00", Time.valueOf("18:00:00"), 18),
    NINETEEN("19:00", Time.valueOf("19:00:00"), 19),
    TWENTY("20:00", Time.valueOf("20:00:00"), 20),
    TWENTYONE("21:00", Time.valueOf("21:00:00"), 21),
    TWENTYTWO("22:00", Time.valueOf("22:00:00"), 22),
    TWENTYTHREE("23:00", Time.valueOf("23:00:00"), 23);
    
    
    private String printTime;
    private Time time;
    private int timeIndex;
    
    /**
     * Number of hours in the class
     */
    public static final int numHours = 24;
    
    EventTime(String printTime, Time time, int timeIndex) {
        this.printTime = printTime;
        this.time = time;
        this.timeIndex = timeIndex;
    }
    
    /**
     * Returns the index representing this time
     * 
     * @return Index for this time in the enum 
     */
    public int getTimeIndex() {
        return timeIndex;
    }
    
    public String printSQLTimeFormat() {
        return time.toString();
    }
    
    @Override
    public String toString() {
        return this.printTime;
    }
    
    /**
     * Returns a list of times between the specified start and end times
     * 
     * @param startTime The time to start at
     * @param endTime The time to end at
     * @return List of times between between the values
     */
    public static List<EventTime> getTimes(EventTime startTime, EventTime endTime) {
        List<EventTime> activeTimes = new ArrayList<EventTime>();
        for (EventTime time : EventTime.values()) {
            if (time.getTime().after(startTime.getTime()) 
                    && time.getTime().before(endTime.getTime())) {
                activeTimes.add(time);
            }
        }
        return activeTimes;
    }
    
    /**
     * Return the time value as Time object
     * 
     * @return Time
     */
    public Time getTime() {
        return this.time;
    }
}
