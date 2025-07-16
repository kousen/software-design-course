package edu.trincoll.dry;

import java.util.regex.Pattern;

// BEFORE: Duplicated validation logic (notice the repetition from UserValidator)
public class CustomerValidator {
    
    public static boolean validateCustomer(Customer customer) {
        // DUPLICATE: Same email validation as UserValidator
        if (customer.email() == null || customer.email().trim().isEmpty()) {
            throw new ValidationException("Invalid email format");
        }
        
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        if (!emailPattern.matcher(customer.email()).matches()) {
            throw new ValidationException("Invalid email format");
        }
        
        // DUPLICATE: Same name validation as UserValidator
        if (customer.name() == null || customer.name().trim().isEmpty()) {
            throw new ValidationException("Name cannot be empty");
        }
        
        // Additional phone validation
        if (customer.phone() == null || customer.phone().trim().isEmpty()) {
            throw new ValidationException("Invalid phone format");
        }
        
        String phoneRegex = "^\\(?[0-9]{3}\\)?[-. ]?[0-9]{3}[-. ]?[0-9]{4}$|^[0-9]{3}-[0-9]{4}$";
        Pattern phonePattern = Pattern.compile(phoneRegex);
        if (!phonePattern.matcher(customer.phone()).matches()) {
            throw new ValidationException("Invalid phone format");
        }
        
        return true;
    }
}