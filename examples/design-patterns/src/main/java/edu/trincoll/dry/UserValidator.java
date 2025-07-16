package edu.trincoll.dry;

import java.util.regex.Pattern;

// BEFORE: Duplicated validation logic
public class UserValidator {
    
    public static boolean validateUser(User user) {
        // Duplicate email validation logic
        if (user.email() == null || user.email().trim().isEmpty()) {
            throw new ValidationException("Invalid email format");
        }
        
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        if (!emailPattern.matcher(user.email()).matches()) {
            throw new ValidationException("Invalid email format");
        }
        
        // Duplicate name validation logic
        if (user.name() == null || user.name().trim().isEmpty()) {
            throw new ValidationException("Name cannot be empty");
        }
        
        return true;
    }
}