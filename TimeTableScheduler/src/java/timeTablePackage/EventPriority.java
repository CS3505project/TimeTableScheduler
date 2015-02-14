/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

/**
 *
 * @author Pc
 */
public enum EventPriority {
    LECTURE(2, "lecture"),
    PRACTICAL(1, "practical"),
    MEETING(0, "meeting");
    
    private int priority;
    private String eventOfPriority;
    
    public static final int numPriority = 3;
    
    EventPriority(int priority, String eventOfPriority) {
        this.priority = priority;
        this.eventOfPriority = eventOfPriority;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public String getEventOfPriority() {
        return eventOfPriority;
    }
    
    public static EventPriority convertToEventPriority(int priority) {
        EventPriority eventPriority = null;
        for (EventPriority curPriority : EventPriority.values()) {
            if (curPriority.getPriority() == priority) {
                eventPriority = curPriority;
            }
        }
        return eventPriority;
    }
}
