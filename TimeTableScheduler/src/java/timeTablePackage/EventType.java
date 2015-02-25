/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timeTablePackage;

/**
 *
 * @author Pc
 */
public enum EventType {
    LECTURE ("lecture"),
    MEETING ("meeting"),
    PRACTICAL ("practical"),
    ALL_EVENTS ("all");
    
    private String name;
    
    EventType(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public static EventType getEventType(String event) {
        EventType eventType = EventType.ALL_EVENTS;
        for (EventType type : EventType.values()) {
            if (type.getName().equals(event)) {
                eventType = type;
            }
        }
        return eventType;
    }
    
    public boolean equals(EventType type) {
        return this.name.equals(type.getName());
    }
}
