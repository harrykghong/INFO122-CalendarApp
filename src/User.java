import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class User {
    private String userName;
    private Map<String, MyCalendar> calendarPool;

    public User(String userName){
        this.userName = userName;
        calendarPool = new HashMap<>();
    }
    public boolean addCalendar(String calendarName){
        if(!checkIfCalendarExists(calendarName)){
            calendarPool.put(calendarName, new GregorianCalendar(calendarName));
            return true;
        }
        return false;
    }
    public boolean switchCalendar(String calendarName){
        return checkIfCalendarExists(calendarName);
    }
    public boolean removeCalendar(String calendarName){
        if(!checkIfCalendarExists(calendarName)){
            return false;
        }
        calendarPool.remove(calendarName);
        return true;
    }

    public void removeAllCalendar(){
        calendarPool = new HashMap<>();
    }
    public MyCalendar getCalendar(String calendarName){
        return calendarPool.get(calendarName);
    }

    public Map<String, MyCalendar> getCalendarPool(){
        return calendarPool;
    }

    public boolean checkIfCalendarExists(String calendarName){
        return calendarPool.containsKey(calendarName);
    }
}
