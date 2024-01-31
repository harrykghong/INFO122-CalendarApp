import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CalendarApp {
    private static UserManager userManager;
    private static StringBuilder currentState;

    private static User currentU;
    private static String currentUser = "";
    private static String currentCalendar = "";
    private static String currentEvent = "";
    static void loop() {
        while (true) {
            Scanner keyboard = new Scanner(System.in);
            currentState = new StringBuilder();
            if(!currentUser.equals("")){
                currentState.append("Current States: ");
                currentState.append("user: " + currentUser);
            }
            if(!currentCalendar.equals("")){
                currentState.append("calendar: " + currentCalendar);
            }
            if(!currentEvent.equals("")){
                currentState.append("event: " + currentEvent);
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
                    System.out.println("'switch user' to manipulate user.");
                    System.out.println("'remove user' to remove user from current user pool.");
                    System.out.println("'user list' to see all available user in user pool");
                    break;
                case "calendar helper":
                    System.out.println("'new calendar' to create a new calendar.");
                    System.out.println("'switch calendar' to manipulate calendar.");
                    System.out.println("'remove calendar' to remove calendar from current user's calendar pool.");
                    break;
                case "event helper":
                    System.out.println("'new event' to create a new event.");
                    System.out.println("'modify event' to modify event.");
                    System.out.println("'remove event' to remove event from current calendar's event pool.");
                    break;
                case "new user":
                    Scanner newUser = new Scanner(System.in);
                    System.out.print("Please input a unique user name: ");
                    String userName = newUser.nextLine();
                    if(userManager.addUser(userName)){
                        System.out.println("Successfully add new user " + userName + "!");
                    }else{
                        System.out.println("Error: User already exist! Please user 'switch user' to manipulate your calendar!");
                    }
                    break;
                case "switch user":
                    Scanner switchUser = new Scanner(System.in);
                    System.out.print("Please input user name you want to switch to: ");
                    String switchUserName = switchUser.nextLine();
                    if(userManager.switchUser(switchUserName)){
                        currentU = userManager.getUser(switchUserName);
                        currentUser = switchUserName;
                        System.out.println("Successfully switch to user " + switchUser + "!");
                    }else{
                        System.out.println("Error: User not exist! Please user 'new user' to initiate a user!");
                    }
                    break;
                case "remove user":
                    Scanner removeUser = new Scanner(System.in);
                    System.out.print("Please type in user name your want to remove: ");
                    String removeUserName = removeUser.nextLine();
                    if(currentUser.equals(removeUserName)){
                        System.out.println("Error: Cannot remove the user your are currently using!");
                        break;
                    }
                    if(userManager.removeUser(removeUserName)){
                        System.out.println("Successfully remove user " + removeUserName + "!");
                    }else{
                        System.out.println("Error: User not exist!");
                    }
                    break;
                case "user list":
                    System.out.print("All users: ");
                    Map<String, User> userpool = UserManager.getUserPool();
                    userpool.forEach((key, value) -> {
                        System.out.print(key + " | ");
                    });
                    System.out.println("");

                default:
                    System.out.println("Invalid Input!");
            }
        }
    }
    public void run() {
        System.out.println("Welcome to MyCalendar!!");
        System.out.println("Type | user helper | calendar helper | event helper | to start Calendar APP!");
        userManager = UserManager.getInstance();
        loop();
    }
}