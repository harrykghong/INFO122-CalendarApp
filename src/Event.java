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

    public LocalDateTime getStartTime(){
        return startTime;
    }

    public LocalDateTime getEndTime(){
        return endTime;
    }

    @Override
    public int compareTo(Event other) {
        return this.startTime.compareTo(other.startTime);
    }

    // Override equals and hashCode if necessary
}
