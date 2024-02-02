import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class User {
    private final String userName;
    private Map<String, MyCalendar> calendarPool;

    public User(String userName){
        this.userName = userName;
        calendarPool = new HashMap<>();
    }

    public String getUserName(){
        return userName;
    }

    public boolean addCalendar(String calendarName){
        if(!checkIfCalendarExists(calendarName)){
            calendarPool.put(calendarName, new GregorianCalendar(calendarName));
            return true;
        }
        return false;
    }

    public void modifyCalendar(String oldC, String newC){
        MyCalendar temp = calendarPool.get(oldC);
        temp.updateCalendarName(newC);
        calendarPool.remove(oldC);
        calendarPool.put(newC, temp);
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

    public String getCalendarPool(){
        StringBuilder allCalendar = new StringBuilder();
        calendarPool.forEach((key, value) -> {
            allCalendar.append(value.getCalendarName()).append(" | ");
        });
        return allCalendar.toString();
    }

    public boolean checkIfCalendarExists(String calendarName){
        return calendarPool.containsKey(calendarName);
    }
}
