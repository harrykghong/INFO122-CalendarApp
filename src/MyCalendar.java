import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public abstract class MyCalendar {
    private String calendarName;
    private Map<String, Event> eventPool;
    public MyCalendar(String calendarName){
        this.calendarName = calendarName;
        this.eventPool = new HashMap<>();
    }

    public boolean addEvent(String eventName, LocalDateTime startTime, LocalDateTime endTime){
        if(!checkIfEventExists(eventName)){
            eventPool.put(eventName, new Event(eventName, startTime, endTime));
            return true;
        }
        return false;
    }
    public boolean removeEvent(String eventName){
        if(checkIfEventExists(eventName)){
            eventPool.remove(eventName);
            return true;
        }
        return false;
    }

    public void removeAllEvent(){
        eventPool = new HashMap<>();
    }

    public String getAllEvents(){
        StringBuilder allEvents = new StringBuilder();
        eventPool.forEach((key, value) -> {
            allEvents.append(key).append(" | ").append(value.getStartTime().toString()).append(" to ").append( value.getEndTime().toString()).append("\n");
        });
        return allEvents.toString();
    }

    public boolean checkIfEventExists(String eventName){
        return eventPool.containsKey(eventName);
    }
}

