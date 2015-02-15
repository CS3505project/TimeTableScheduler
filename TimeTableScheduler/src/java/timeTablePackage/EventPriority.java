/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

/**
 * Represents the priority values for various events in the system
 * 
 * @author John O Riordan
 */
public enum EventPriority {
    LECTURE(2, "lecture"),
    PRACTICAL(1, "practical"),
    MEETING(0, "meeting");
    
    private int priority;
    private String eventOfPriority;
    
    /**
     * Number of different priorities
     */
    public static final int numPriority = 3;
    
    EventPriority(int priority, String eventOfPriority) {
        this.priority = priority;
        this.eventOfPriority = eventOfPriority;
    }
    
    /**
     * Returns the priority
     * 
     * @return Event priority 
     */
    public int getPriority() {
        return priority;
    }
    
    /**
     * Returns the event this priority belongs to as a string
     * For example "lecture" for EventPriority.LECTURE
     * @return 
     */
    public String getEventOfPriority() {
        return eventOfPriority;
    }
    
    /**
     * Converts the priority to an EventPriority object
     * May return null if the priority is incorrect
     * 
     * @param priority The priority of the event
     * @return The event priority object for this priority
     */
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
