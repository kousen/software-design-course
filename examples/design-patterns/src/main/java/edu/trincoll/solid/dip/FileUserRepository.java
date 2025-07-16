package edu.trincoll.solid.dip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUserRepository implements UserRepository {
    private Map<Long, User> fileStorage = new HashMap<>();
    
    @Override
    public void save(User user) {
        fileStorage.put(user.getId(), user);
        System.out.println("User saved to file: " + user);
    }
    
    @Override
    public User findById(Long id) {
        User user = fileStorage.get(id);
        System.out.println("User retrieved from file: " + user);
        return user;
    }
    
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>(fileStorage.values());
        System.out.println("All users retrieved from file: " + users.size() + " users");
        return users;
    }
    
    @Override
    public void delete(Long id) {
        User removed = fileStorage.remove(id);
        System.out.println("User deleted from file: " + removed);
    }
}