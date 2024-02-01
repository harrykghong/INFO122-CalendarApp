import java.time.LocalDateTime;

public class Event implements Comparable<Event> {
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    // other properties like participants, location, etc.

    public Event(String title, LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public String getEventTitle(){
        return title;
    }
    public LocalDateTime getStartTime(){
        return startTime;
    }

    public LocalDateTime getEndTime(){
        return endTime;
    }

    public void updateEventTitle(String value){
        title = value;
    }
    public void updateEventStartTime(String value){
        startTime = LocalDateTime.parse(value);
    }
    public void updateEventEndTime(String value){
        endTime = LocalDateTime.parse(value);
    }

    @Override
    public int compareTo(Event other) {
        return this.startTime.compareTo(other.startTime);
    }

    // Override equals and hashCode if necessary
}
