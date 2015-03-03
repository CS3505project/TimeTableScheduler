package timeTablePackage;

import java.sql.Date;
import java.sql.Time;

/**
 * Represents a practical in the timetable
 * 
 * @author John O Riordan
 */
public class Practical extends Event {
    private String semester;
    private Day weekDay;
    private Date endDate;

    public Practical(String moduleCode, String semester, int weekDay, Time time, 
                     String room, Date date, Date endDate) {
        super(moduleCode, date, time, room);
        this.semester = semester;
        this.weekDay = Day.convertToDay(weekDay);
        this.endDate = endDate;
    }
    
    @Override
    public String toString() {
        // To-Do
        return "Practical";
    }
    
    @Override
    public EventType getEventType() {
        return EventType.PRACTICAL;
    }
    
    @Override
    public EventPriority getEventPriority() {
        return EventPriority.PRACTICAL;
    }

    /**
     * Returns the semester the practical is held
     * 
     * @return The semester 
     */
    public String getSemester() {
        return semester;
    }
    
    /**
     * Sets the practical to a new semester
     * 
     * @param semester The new semester
     */
    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    @Override
    public int getDayOfWeek() {
        return weekDay.getIndex();
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

    /**
     * Returns the end date for the practical
     * 
     * @return End date for the practical
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date for this practical to a new date
     * 
     * @param endDate New end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
}
