import java.util.HashMap;
import java.util.Map;

// singleton pattern
public class UserManager {
    private static Map<String, User> userPool;

    private static UserManager userManager;
    private UserManager(){
        userPool = new HashMap<>();
    }
    public boolean addRegularUser(String userName){
        if(!checkIfUserExists(userName)){
            User newRegularUser = new RegularUser(userName);
            userPool.put(userName, newRegularUser);
            return true;
        }
        return false;
    }
    public boolean switchUser(String userName){
        return checkIfUserExists(userName);
    }
    public boolean removeUser(String userName){
        if(!checkIfUserExists(userName)){
            return false;
        }
        userPool.remove(userName);
        return true;
    }
    public User getUser(String userName){
        return userPool.get(userName);
    }
    public String getUserPool(){
        StringBuilder allUsers = new StringBuilder();
        userPool.forEach((key, value) -> {
            allUsers.append(value.getUserName()).append(" | ");
        });
        return allUsers.toString();
    }
    public boolean checkIfUserExists(String userName){
        return userPool.containsKey(userName);
    }

    public static UserManager getInstance(){
        if(userManager == null){
            userManager = new UserManager();
        }
        return userManager;
    }
}


