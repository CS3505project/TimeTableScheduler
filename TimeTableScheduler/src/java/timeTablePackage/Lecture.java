/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.Date;
import java.sql.Time;

/**
 * Represents a lecture object in the timetable
 * 
 * @author John O Riordan
 */
public class Lecture extends Event {
    private String semester;
    private Day weekDay;
    private Date endDate;

    public Lecture(String moduleCode, String semester, int weekDay, Time time, String room, Date date, Date endDate) {
        super(moduleCode, date, time, room);
        this.semester = semester;
        this.weekDay = Day.convertToDay(weekDay);
        this.endDate = endDate;
    }
    
    public Lecture(String moduleCode, Date date, Time time, String room) {
        super(moduleCode, date, time, room);
    }
    
    @Override
    public String toString() {
        // To-Do
        return "";
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    /**
     * Return the day of the week as a Day object
     * 
     * @return Day object representing the day 
     */
    public Day getWeekDay() {
        return weekDay;
    }
    
    /**
     * Return the index corresponding to this day
     * 
     * @return The index for this day 
     */
    public int getWeekDayIndex() {
        return weekDay.getIndex();
    }
    
    /**
     * Set the day using a Day object
     * 
     * @param weekDay New Day object
     */
    public void setWeekDay(Day weekDay) {
        this.weekDay = weekDay;
    }

    /**
     * Set the day by providing an index
     * May set to null if incorrect index value
     * 
     * @param index Index value for the day 
     */
    public void setWeekDayIndex(int index) {
        this.weekDay = Day.convertToDay(index);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
