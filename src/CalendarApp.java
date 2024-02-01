import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CalendarApp {
    private static UserManager userManager;
    private static User currentU;
    private static MyCalendar currentC;
    private static Event currentE;
    private static String currentUser = "";
    private static String currentCalendar = "";
    static void loop() {
        while (true) {
            Scanner keyboard = new Scanner(System.in);
            StringBuilder currentState = new StringBuilder();
            if(!currentUser.isEmpty()){
                currentState.append("Current Status--");
                currentState.append(" User: ").append(currentUser);
            }
            if(!currentCalendar.isEmpty()){
                currentState.append(" Calendar: ").append(currentCalendar);
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
                    System.out.println("'modify calendar' to modify calendar name.");
                    System.out.println("'switch calendar' to operate specific calendar.");
                    System.out.println("'remove calendar' to remove calendar from current user's calendar pool.");
                    System.out.println("'calendar list' to see all available calendar for current user");
                    break;
                case "event helper":
                    System.out.println("'new event' to create a new event.");
                    System.out.println("'modify event' to modify event.");
                    System.out.println("'remove event' to remove event from current calendar's event pool.");
                    System.out.println("'event list' to see all available event for current user's current calendar");
                    break;
                case "new user":
                    Scanner newUser = new Scanner(System.in);
                    System.out.print("Please input a unique user name: ");
                    String userName = newUser.nextLine();
                    if(userManager.addRegularUser(userName)){
                        System.out.println("Successfully add new user [" + userName + "]!");
                    }else{
                        System.out.println("Error: User already exist!");
                    }
                    break;
                case "switch user":
                    Scanner switchUser = new Scanner(System.in);
                    System.out.print("Please input user name you want to switch to: ");
                    String switchUserName = switchUser.nextLine();
                    if(userManager.switchUser(switchUserName)){
                        currentU = userManager.getUser(switchUserName);
                        currentUser = switchUserName;
                        System.out.println("Successfully switch to user [" + switchUserName + "]!");
                    }else{
                        System.out.println("Error: User not exist!");
                    }
                    break;
                case "remove user":
                    Scanner removeUser = new Scanner(System.in);
                    System.out.print("Please type in user name you want to remove: ");
                    String removeUserName = removeUser.nextLine();
                    if(userManager.removeUser(removeUserName)){
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
                    System.out.println(userManager.getUserPool());
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
                            System.out.println("Error: Calendar not exist!");
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
                case "modify calendar":
                    if(currentU == null){
                        System.out.println("Error: Select which user you want to operate!");
                    }else{
                        Scanner modifyCalendar = new Scanner(System.in);
                        System.out.print("Please indicate which calendar you want to modify: ");
                        String modifyCalendarName = modifyCalendar.nextLine();
                        if (currentU.checkIfCalendarExists(modifyCalendarName)) {
                            if(currentCalendar.equals(modifyCalendarName)){
                                System.out.println("Error: Cannot modify the calendar you are currently operating");
                            }else{
                                Scanner newCalendar = new Scanner(System.in);
                                System.out.print("Please input a new unique calendar name: ");
                                String newCalendarName = newCalendar.nextLine();
                                currentU.modifyCalendar(modifyCalendarName, newCalendarName);
                                System.out.println("Successfully modify calendar [" + modifyCalendarName + "] to " + "[" + newCalendarName + "]!");
                            }
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
                        System.out.println(currentU.getCalendarPool());
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
                            System.out.println("Successfully remove event [" + removeEventName + "]!");
                        } else {
                            System.out.println("Error: Event not exist!");
                        }
                    }
                    break;
                case "modify event":
                    if(currentC == null){
                        System.out.println("Error: Select which calendar you want to operate!");
                    }else if(currentC.isCalendarEmpty()) {
                        System.out.println("Error: Your calendar [" + currentCalendar + "] does not have any event to operate!");
                    }else {
                        // require time or event name or description update
                        // using 1. 2. 3. to indicate
                        Scanner updateEvent = new Scanner(System.in);
                        boolean updated = false;
                        do{
                            System.out.print("Please input event name you want to update ('quit' to cancel event modification): ");
                            String updateEventName = updateEvent.nextLine();
                            if(updateEventName.equals("quit")){
                                break;
                            }
                            if(!currentC.checkIfEventExists(updateEventName)){
                                System.out.println("Error: Event not exist!");
                                continue;
                            }
                            currentE = currentC.getEvent(updateEventName);
                            do {
                                System.out.print("Please indicate which aspect of event you want to update:\n1. Event Title  2. Event Start Time  3. Event End Time\n");
                                Scanner eventCommand = new Scanner(System.in);
                                String command = eventCommand.nextLine();
                                Scanner commandValue;
                                String value;
                                switch (command) {
                                    case ("1"):
                                        System.out.print("Please provide the new unique event title: ");
                                        commandValue = new Scanner(System.in);
                                        value = commandValue.nextLine();
                                        currentC.updateEventTitle(updateEventName, value);
                                        System.out.println("Successfully update your event title!");
                                        updated = true;
                                        break;
                                    case ("2"):
                                        System.out.print("Please provide the new start event time (e.g. 2024-01-01T10:00): ");
                                        commandValue = new Scanner(System.in);
                                        value = commandValue.nextLine();
                                        currentE.updateEventStartTime(value);
                                        System.out.println("Successfully update your event start time!");
                                        updated = true;
                                        break;
                                    case ("3"):
                                        System.out.println("Please provide the new end event time (e.g. 2024-01-01T10:00): ");
                                        commandValue = new Scanner(System.in);
                                        value = commandValue.nextLine();
                                        currentE.updateEventEndTime(value);
                                        System.out.println("Successfully update your event end time!");
                                        updated = true;
                                        break;
                                    default:
                                        System.out.println("Error: Invalid command");
                                }
                            } while (!updated);
                        } while (!updated);
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
                }
                currentCalendar = "";
                currentC = null;
                break;
        }
    }
    public void run() {
        System.out.println("Welcome to MyCalendar!!");
        System.out.println("Type | user helper | calendar helper | event helper | to start Calendar APP!");
        userManager = UserManager.getInstance();
        loop();
    }
}