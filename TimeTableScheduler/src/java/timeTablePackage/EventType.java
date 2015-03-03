package timeTablePackage;

/**
 * Represents the event types that can be created
 * 
 * @author John O Riordan
 */
public enum EventType {
    LECTURE ("lecture"),
    MEETING ("meeting"),
    PRACTICAL ("practical"),
    ALL_EVENTS ("all");
    
    private final String name;
    
    EventType(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of the event type
     * @return event type name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the event type object for the event name
     * 
     * @param event The name of the event
     * @return Event type object
     */
    public static EventType getEventType(String event) {
        EventType eventType = EventType.ALL_EVENTS;
        for (EventType type : EventType.values()) {
            if (type.getName().equals(event)) {
                eventType = type;
            }
        }
        return eventType;
    }
    
    /**
     * Returns true if the event types are equal
     * The names are compared
     * 
     * @param type the type to compare
     * @return true if the same event
     */
    public boolean equals(EventType type) {
        return this.name.equals(type.getName());
    }
}
