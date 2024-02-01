import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CalendarApp {
    private static UserManager userManager;
    private static StringBuilder currentState;

    private static User currentU;
    private static MyCalendar currentC;
    private static Event currentE;
    private static String currentUser = "";
    private static String currentCalendar = "";
    private static String currentEvent = "";
    static void loop() {
        while (true) {
            Scanner keyboard = new Scanner(System.in);
            currentState = new StringBuilder();
            if(!currentUser.isEmpty()){
                currentState.append("Current States: ");
                currentState.append("user: ").append(currentUser);
            }
            if(!currentCalendar.isEmpty()){
                currentState.append(" calendar: ").append(currentCalendar);
            }
            if(!currentEvent.equals("")){
                currentState.append(" event: ").append(currentEvent);
            }
            if(currentState.length() != 0){
                System.out.println(currentState);
            }
            String input = keyboard.nextLine();
            if (input.equals("exit")) {
                System.out.println("Exiting myCalendar");
                break;
            }

            switch (input) {
                case "user helper":
                    System.out.println("'new user' to create a new user.");
                    System.out.println("'switch user' to operate specific user.");
                    System.out.println("'remove user' to remove user from current user pool.");
                    System.out.println("'user list' to see all available user in user pool");
                    break;
                case "calendar helper":
                    System.out.println("'new calendar' to create a new calendar.");
                    System.out.println("'switch calendar' to operate specific calendar.");
                    System.out.println("'remove calendar' to remove calendar from current user's calendar pool.");
                    System.out.println("'calendar list' to see all available calendar for current user");
                    break;
                case "event helper":
                    System.out.println("'new event' to create a new event.");
//                    System.out.println("'modify event' to modify event.");
                    System.out.println("'remove event' to remove event from current calendar's event pool.");
                    System.out.println("'event list' to see all available event for current user's current calendar");
                    break;
                case "new user":
                    Scanner newUser = new Scanner(System.in);
                    System.out.print("Please input a unique user name: ");
                    String userName = newUser.nextLine();
                    if(UserManager.addRegularUser(userName)){
                        System.out.println("Successfully add new user [" + userName + "]!");
                    }else{
                        System.out.println("Error: User already exist!");
                    }
                    break;
                case "switch user":
                    Scanner switchUser = new Scanner(System.in);
                    System.out.print("Please input user name you want to switch to: ");
                    String switchUserName = switchUser.nextLine();
                    if(UserManager.switchUser(switchUserName)){
                        currentU = UserManager.getUser(switchUserName);
                        currentUser = switchUserName;
                        System.out.println("Successfully switch to user [" + switchUserName + "]!");
                    }else{
                        System.out.println("Error: User not exist! Please user 'new user' to initiate a user!");
                    }
                    break;
                case "remove user":
                    Scanner removeUser = new Scanner(System.in);
                    System.out.print("Please type in user name you want to remove: ");
                    String removeUserName = removeUser.nextLine();
                    if(UserManager.removeUser(removeUserName)){
                        if(currentUser.equals(removeUserName)){
                            resetStates("user"); // remove currently operating user
                        }
                        System.out.println("Successfully remove user [" + removeUserName + "]!");
                    }else{
                        System.out.println("Error: User not exist!");
                    }
                    break;
                case "user list":
                    System.out.print("All users: ");
                    Map<String, User> userPool = UserManager.getUserPool();
                    userPool.forEach((key, value) -> {
                        System.out.print(key + " | ");
                    });
                    System.out.println();
                    break;
                case "new calendar":
                    if(currentU == null){
                        System.out.println("Error: Select which user you want to operate!");
                    }else{
                        Scanner newCalendar = new Scanner(System.in);
                        System.out.print("Please input a unique calendar name: ");
                        String calendarName = newCalendar.nextLine();
                        if(currentU.addCalendar(calendarName)){
                            System.out.println("Successfully add new calendar [" + calendarName + "]!");
                        }else{
                            System.out.println("Error: Calendar already exist!");
                        }
                    }
                    break;
                case "switch calendar":
                    if(currentU == null){
                        System.out.println("Error: Select which user you want to operate!");
                    }else {
                        Scanner switchCalendar = new Scanner(System.in);
                        System.out.print("Please input calendar name you want to switch to: ");
                        String switchCalendarName = switchCalendar.nextLine();
                        if (currentU.switchCalendar(switchCalendarName)) {
                            currentC = currentU.getCalendar(switchCalendarName);
                            currentCalendar = switchCalendarName;
                            System.out.println("Successfully switch to calendar [" + switchCalendarName + "]!");
                        } else {
                            System.out.println("Error: Calendar not exist! Please user 'new calendar' to initiate a calendar!");
                        }
                    }
                    break;
                case "remove calendar":
                    if(currentU == null){
                        System.out.println("Error: Select which user you want to operate!");
                    }else {
                        Scanner removeCalendar = new Scanner(System.in);
                        System.out.print("Please input calendar name you want to remove: ");
                        String removeCalendarName = removeCalendar.nextLine();
                        if (currentU.removeCalendar(removeCalendarName)) {
                            if(currentCalendar.equals(removeCalendarName)){
                                resetStates("calendar"); // remove currently using calendar
                            }
                            System.out.println("Successfully remove calendar [" + removeCalendarName + "]!");
                        } else {
                            System.out.println("Error: Calendar not exist!");
                        }
                    }
                    break;
                case "calendar list":
                    if(currentU == null){
                        System.out.println("Error: Select which user you want to operate!");
                    }else {
                        System.out.print("All calendars of user [" + currentUser + "] : ");
                        Map<String, MyCalendar> calendarPool = currentU.getCalendarPool();
                        calendarPool.forEach((key, value) -> {
                            System.out.print(key + " | ");
                        });
                        System.out.println();
                    }
                    break;
                case "new event":
                    if(currentC == null){
                        System.out.println("Error: Select which calendar you want to operate!");
                    }else{
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Enter the unique title of the event:");
                        String title = scanner.nextLine();

                        System.out.println("Enter the start date and time of the event (e.g., 2024-01-01T08:00):");
                        String startDateTimeString = scanner.nextLine();
                        LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                        System.out.println("Enter the end date and time of the event (e.g., 2024-01-01T10:00):");
                        String endDateTimeString = scanner.nextLine();
                        LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                        if(currentC.addEvent(title, startDateTime, endDateTime)){
                            System.out.println("Successfully add new event [" + title + "]!");
                        }else{
                            System.out.println("Error: Event already exist!");
                        }
                    }
                    break;
                case "remove event":
                    if(currentC == null){
                        System.out.println("Error: Select which calendar you want to operate!");
                    }else {
                        Scanner removeEvent = new Scanner(System.in);
                        System.out.print("Please input event name you want to remove: ");
                        String removeEventName = removeEvent.nextLine();
                        if (currentC.removeEvent(removeEventName)) {
                            if(currentEvent.equals(removeEventName)){
                                resetStates("event"); // remove currently using event
                            }
                            System.out.println("Successfully remove calendar [" + removeEventName + "]!");
                        } else {
                            System.out.println("Error: Event not exist!");
                        }
                    }
                    break;
                case "update event":
                    if(currentC == null){
                        System.out.println("Error: Select which calendar you want to operate!");
                    }else {
                        // require time or event name or description update
                        // using 1. 2. 3. to indicate
                    }
                    break;
                case "event list":
                    if(currentC == null){
                        System.out.println("Error: Select which calendar you want to operate!");
                    }else {
                        System.out.println("All events of user [" + currentUser + "] in calendar [" + currentCalendar + "]: ");
                        System.out.print(currentC.getAllEvents());
                    }
                    break;
                case "":
                    break;
                default:
                    System.out.println("Invalid Input!");
            }
        }
    }

    private static void resetStates(String command){
        switch(command){
            case "user":
                if(currentE != null){
                    currentU.getCalendar(currentCalendar).removeAllEvent();
                    currentEvent = "";
                    currentE = null;
                }
                if(currentC != null){
                    currentU.removeAllCalendar();
                    currentCalendar = "";
                    currentC = null;
                }
                currentUser = "";
                currentU = null;
                break;
            case "calendar":
                if(currentE != null){
                    currentU.getCalendar(currentCalendar).removeAllEvent();
                    currentEvent = "";
                    currentE = null;
                }
                currentCalendar = "";
                currentC = null;
                break;
            case "event":
                currentEvent = "";
                currentE = null;
        }
    }
    public void run() {
        System.out.println("Welcome to MyCalendar!!");
        System.out.println("Type | user helper | calendar helper | event helper | to start Calendar APP!");
        userManager = UserManager.getInstance();
        loop();
    }
}