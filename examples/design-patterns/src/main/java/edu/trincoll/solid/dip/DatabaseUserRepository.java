package edu.trincoll.solid.dip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseUserRepository implements UserRepository {
    private Map<Long, User> database = new HashMap<>();
    
    @Override
    public void save(User user) {
        database.put(user.getId(), user);
        System.out.println("User saved to database: " + user);
    }
    
    @Override
    public User findById(Long id) {
        User user = database.get(id);
        System.out.println("User retrieved from database: " + user);
        return user;
    }
    
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>(database.values());
        System.out.println("All users retrieved from database: " + users.size() + " users");
        return users;
    }
    
    @Override
    public void delete(Long id) {
        User removed = database.remove(id);
        System.out.println("User deleted from database: " + removed);
    }
}