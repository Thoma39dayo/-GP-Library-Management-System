package services;
import models.User;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public void registerUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        users.put(user.getId(), user);
    }

    public User authenticate(String id, String password, Class<?> userType) {
        if (id == null || password == null) {
            throw new IllegalArgumentException("ID and password cannot be null.");
        }
        User user = users.get(id);
        if (user != null && user.authenticate(password)) {
            if (userType.isInstance(user)) {
                return user;
            } else {
                System.out.println("Error: Incorrect user role.");
            }
        }
        return null;
    }


}
