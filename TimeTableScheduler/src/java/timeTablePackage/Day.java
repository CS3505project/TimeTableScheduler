/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

/**
 * Represents each day of the week.
 * Returns the specific index for each day which can be 
 * stored in the database
 * 
 * @author John O Riordan
 */
public enum Day {
    MONDAY("Monday", 1),
    TUESDAY("Tuesday", 2),
    WEDNESDAY("Wednesday", 3),
    THURSDAY("Thursday", 4),
    FRIDAY("Friday", 5),
    SATURDAY("Saturday", 6),
    SUNDAY("Sunday", 7);
    
    
    private String day;
    private int index;
    
    Day(String day, int index) {
        this.day = day;
        this.index = index;
    }
    
    /**
     * Get the day as a string
     * 
     * @return Day of week
     */
    public String getDay() {
        return this.day;
    }
    
    /**
     * Convert the index provided to its corresponding day
     * Returns null if not a valid index
     * 
     * @param index The index to convert to a day
     * @return The day object
     */
    public static Day convertToDay(int index) {
        // iterate through the days until the indexes match
        for (Day day : Day.values()) {
            if (day.getIndex() == index) {
                return day;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return this.day;
    }
    
    /**
     * Return the index for the day
     * 
     * @return Index of the day
     */
    public int getIndex() {
        return this.index;
    }
}
