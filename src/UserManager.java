import java.util.HashMap;
import java.util.Map;
// singleton pattern
public class UserManager {
    private static Map<String, User> userPool;

    private static UserManager userManager;
    private UserManager(){
        userPool = new HashMap<>();
    }
    public static boolean addUser(String userName){
        if(!checkIfUserExists(userName)){
            User newUser = new User(userName);
            userPool.put(userName, newUser);
            return true;
        }
        return false;
    }
    public static boolean switchUser(String userName){
        if(checkIfUserExists(userName)){

            return true;
        }
        return false;
    }
    public static User getUser(String userName){
        return userPool.get(userName);
    }

    public static Map<String, User> getUserPool(){
        return userPool;
    }
    public static boolean removeUser(String userName){
        if(!checkIfUserExists(userName)){
            return false;
        }
        userPool.remove(userName);
        return true;
    }


    public static boolean checkIfUserExists(String userName){
        return userPool.containsKey(userName);
    }

    public static UserManager getInstance(){
        if(userManager == null){
            userManager = new UserManager();
        }
        return userManager;
    }
}
