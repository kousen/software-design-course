package edu.trincoll.solid.dip;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public void createUser(String name, String email) {
        Long id = System.currentTimeMillis(); // Simple ID generation
        User user = new User(id, name, email);
        userRepository.save(user);
    }
    
    public User getUser(Long id) {
        return userRepository.findById(id);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public void deleteUser(Long id) {
        userRepository.delete(id);
    }
}