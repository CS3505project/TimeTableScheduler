package timeTablePackage;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a specific time slot in timetable
 *
 * @author John O Riordan
 */
public class TimeSlot {
    private final int[] eventPrioritys;
    private int totalPriority;
    private boolean suggested;
    private final List<Event> events;
    private final String date;
    private final String time;
    
    public TimeSlot(String date, String time) {
        this.date = date;
        this.time = time;
        this.eventPrioritys = new int[EventPriority.highestPriority + 1];
        this.totalPriority = 0;
        events = new ArrayList<Event>();
    }

    /**
     * Checks if there are events in this time slot
     * @return True if an event exists
     */
    public boolean hasEvents() {
        return totalPriority > 0 || !events.isEmpty();
    }
    
    /**
     * Returns the list of events scheduled for this time slot
     * @return 
     */
    public List<Event> getEvents() {
        return events;
    }
    
    /**
     * Adds an event to this timeslot
     * @param event The event
     */
    public void addEvent(Event event) {
        events.add(event);
    }
    
    /**
     * Set if this timeslot is a suggested timeslot for a meeting
     * 
     * @param suggested True if suggested
     */
    public void setSuggested(boolean suggested) {
        this.suggested = suggested;
    }
    
    /**
     * Returns if the timeslot was suggested to hold a meeting
     * 
     * @return True if suggested
     */
    public boolean isSuggested() {
        return suggested;
    }
    
    /**
     * Total priority for all eventPrioritys scheduled during this timeslot
     * 
     * @return total priority
     */
    public int getTotalPriority() {
        return totalPriority;
    }
    
    /**
     * Increments the number of eventPrioritys with this priority value
     * 
     * @param priority Priority of the event scheduled
     */
    public void addPriority(int priority) {
        if (priority >= 0 && priority < eventPrioritys.length) {
            eventPrioritys[priority] += 1;
            totalPriority += priority;
        }
    }
    
    /**
     * Returns the number of lectures in this timeslot
     * 
     * @return Number of lectures
     */
    public int numLectures() {
        return eventPrioritys[EventPriority.LECTURE.getPriority()];
    }
    
    /**
     * Returns the number of practicals in this timeslot
     * 
     * @return Number of practicals
     */
    public int numPracticals() {
        return eventPrioritys[EventPriority.PRACTICAL.getPriority()];
    }
    
    /**
     * Returns the number of meetings in this timeslot
     * 
     * @return Number of meeting
     */
    public int numMeetings() {
        return eventPrioritys[EventPriority.MEETING.getPriority()];
    }
    
    /**
     * Returns the HTML representation of this timeslot that would display in
     * the html table for suggesting a timeslot for a meeting. Includes all the
     * relevant classes and divs to make the timetable interactive
     * 
     * @return HTML td element
     */
    public String printTableCell() {
        if (totalPriority > 0) {
            return "<td class=\"animate selectable priority-" + totalPriority + " "
                   + (isSuggested() ? "suggested-timeslot\"" : "\"") + ">"
                    
                   + "<div class=\"hidden\""
                   + " data-date=\"" + date + "\""
                   + " data-time=\"" + time + "\"></div>"
                    
                   + "Lecture: " + numLectures() + "<br />"
                   + "Practical: " + numPracticals() + "<br />"
                   + "Meeting: " + numMeetings() + "</td>";
        } else {
            return "<td class=\"animate selectable priority-" + totalPriority + " "
                   + (isSuggested() ? "suggested-timeslot\"" : "\"") + ">"
                   + "<div class=\"hidden\""
                   + " data-date=\"" + date + "\""
                   + " data-time=\"" + time + "\"></div>"
                   + "</td>";
        }
    }
    
    /**
     * Prints a detailed version of the events details
     * @param filter Filter the events displayed
     * @param clickable If true it makes the time slot interactive for certain forms
     * @return HTML to print into a table cell
     */
    public String printDetailedTableCell(EventType filter, boolean clickable, String userId) {
        EventPriority highPriority = EventPriority.MEETING;
        String eventList = "";
        String description = "";
        String removeLink = "";
        for (Event event : events) {
            if (filterEvent(event.getEventType(), filter)) {
                if (event.getEventType().equals(EventType.MEETING) && ((Meeting)event).getOrganiser().equals(userId)) {
                    removeLink += "<div class=\"innerEvent\"><a href=\"deleteEvent.jsp?eventId=" + event.getEventID() + "\">Remove</a></div>";
                } else {
                    removeLink = "";
                }
                
                eventList += event.toString() + removeLink + "<br />";
                description += getEventDescription(event);
                highPriority = (event.getEventPriority().getPriority() > highPriority.getPriority() 
                                            ? event.getEventPriority() : highPriority);
                
            }
        }
        String html = "<td class=\"";
        
        if (clickable) {
            html += "animate selectable";
        }
        
        if (eventList.equals("")) {
            html = "<td>";
        } else {
            html += " " + highPriority.getPriorityName() + " hoverable\"" +
                    " data-description=\"" + description + "\">" ;
        }
        if (clickable) {
            html += "<div class=\"hidden\""
                    + " data-date=\"" + date + "\""
                    + " data-time=\"" + time + "\"></div>";
        }
        html += eventList + "</td>";
        return html;
    }
    
    /**
     * Returns a string representation of the details for this event. 
     * This is displayed in the timetable
     * @param event Event to get details
     * @return Details as string
     */
    private String getEventDescription(Event event) {
        String description = "";
        if (event.getEventType().equals(EventType.MEETING)) {
            Meeting meeting = ((Meeting)event);
            description += "Description: " + meeting.getDescription() + "<br />"
                           + "Organiser: " + meeting.retrieveOrganiserDetails() + "<br />";
        } else if (event.getEventType().equals(EventType.LECTURE)) {
            Lecture lecture = ((Lecture)event);
            description += "Module: " + lecture.getEventID() + "<br />"
                           + "Semester: " + lecture.getSemester() + "<br />";
        } else {
            Practical practical = ((Practical)event);
            description += "Module: " + practical.getEventID() + "<br />"
                           + "Semester: " + practical.getSemester() + "<br />";
        }
        description += "Time: " + event.getTime() + "<br />Location: " + event.getLocation() + "<br />";
        return description;
    }
    
    /**
     * Filters the events to be displayed from the time slot
     * 
     * @param eventType The type of the event
     * @param filterType The filter type 
     * @return True if the event should be displayed
     */
    private boolean filterEvent(EventType eventType, EventType filterType) {
        return (eventType.equals(filterType) || filterType.equals(EventType.ALL_EVENTS));
    }
     
    @Override
    public String toString() {
        return "Lecture: " + numLectures() + "\n"
               + "Practical: " + numPracticals() + "\n"
               + "Meeting: " + numMeetings();
    }
}
