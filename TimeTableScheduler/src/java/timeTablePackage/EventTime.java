/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the times the system can display in the timetable
 * 
 * @author John O Riordan
 */
public enum EventTime {
    ONE("1:00", Time.valueOf("01:00:00")),
    TWO("2:00", Time.valueOf("02:00:00")),
    THREE("3:00", Time.valueOf("03:00:00")),
    FOUR("4:00", Time.valueOf("04:00:00")),
    FIVE("5:00", Time.valueOf("05:00:00")),
    SIX("6:00", Time.valueOf("06:00:00")),
    SEVEN("7:00", Time.valueOf("07:00:00")),
    EIGHT("8:00", Time.valueOf("08:00:00")),
    NINE("9:00", Time.valueOf("09:00:00")),
    TEN("10:00", Time.valueOf("10:00:00")),
    ELEVEN("11:00", Time.valueOf("11:00:00")),
    TWELVE("12:00", Time.valueOf("12:00:00")),
    THIRTEEN("13:00", Time.valueOf("13:00:00")),
    FOURTEEN("14:00", Time.valueOf("14:00:00")),
    FIFTHTEEN("15:00", Time.valueOf("15:00:00")),
    SIXTEEN("16:00", Time.valueOf("16:00:00")),
    SEVENTEEN("17:00", Time.valueOf("17:00:00")),
    EIGHTTEEN("18:00", Time.valueOf("18:00:00")),
    NINETEEN("19:00", Time.valueOf("19:00:00")),
    TWENTY("20:00", Time.valueOf("20:00:00")),
    TWENTYONE("21:00", Time.valueOf("21:00:00")),
    TWENTYTWO("22:00", Time.valueOf("22:00:00")),
    TWENTYTHREE("23:00", Time.valueOf("23:00:00")),
    TWENTYFOUR("24:00", Time.valueOf("24:00:00"));
    
    
    private String printTime;
    private Time time;
    
    EventTime(String printTime, Time time) {
        this.printTime = printTime;
        this.time = time;
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
            if (time.getTime().after(startTime.getTime()) && time.getTime().before(endTime.getTime())) {
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
