package edu.trincoll.dry;

import java.util.regex.Pattern;

// AFTER: DRY - Single source of truth for validation logic
public class ValidationUtils {
    
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String PHONE_REGEX = "^\\(?[0-9]{3}\\)?[-. ]?[0-9]{3}[-. ]?[0-9]{4}$|^[0-9]{3}-[0-9]{4}$";
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    
    public static boolean isValidEmail(String email) {
        return email != null && 
               !email.trim().isEmpty() && 
               EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
    
    public static boolean isValidPhone(String phone) {
        return phone != null && 
               !phone.trim().isEmpty() && 
               PHONE_PATTERN.matcher(phone).matches();
    }
    
    public static void validateEmail(String email, String fieldName) {
        if (!isValidEmail(email)) {
            throw new ValidationException("Invalid " + fieldName + " format");
        }
    }
    
    public static void validateName(String name, String fieldName) {
        if (!isValidName(name)) {
            throw new ValidationException(fieldName + " cannot be empty");
        }
    }
    
    public static void validatePhone(String phone, String fieldName) {
        if (!isValidPhone(phone)) {
            throw new ValidationException("Invalid " + fieldName + " format");
        }
    }
}