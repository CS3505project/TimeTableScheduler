/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author jjor1
 */
public class Practical {
    private String moduleCode;
    private String semester;
    private int weekDay;
    private Time time;
    private String room;
    private Date startDate;
    private Date endDate;

    public Practical(String moduleCode, String semester, int weekDay, Time time, String room, Date startDate, Date endDate) {
        this.moduleCode = moduleCode;
        this.semester = semester;
        this.weekDay = weekDay;
        this.time = time;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
}
