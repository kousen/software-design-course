package edu.trincoll.dry;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ValidationExampleTest {
    
    @Test
    void userValidation_shouldAcceptValidUser() {
        var user = new User("john.doe@example.com", "John Doe");
        
        assertThat(UserValidator.validateUser(user)).isTrue();
    }
    
    @Test
    void userValidation_shouldRejectInvalidEmail() {
        var user = new User("invalid-email", "John Doe");
        
        assertThatThrownBy(() -> UserValidator.validateUser(user))
            .isInstanceOf(ValidationException.class)
            .hasMessageContaining("Invalid email format");
    }
    
    @Test
    void userValidation_shouldRejectEmptyName() {
        var user = new User("john@example.com", "");
        
        assertThatThrownBy(() -> UserValidator.validateUser(user))
            .isInstanceOf(ValidationException.class)
            .hasMessageContaining("Name cannot be empty");
    }
    
    @Test
    void customerValidation_shouldAcceptValidCustomer() {
        var customer = new Customer("jane.smith@example.com", "Jane Smith", "555-1234");
        
        assertThat(CustomerValidator.validateCustomer(customer)).isTrue();
    }
    
    @Test
    void customerValidation_shouldRejectInvalidEmail() {
        var customer = new Customer("bad-email", "Jane Smith", "555-1234");
        
        assertThatThrownBy(() -> CustomerValidator.validateCustomer(customer))
            .isInstanceOf(ValidationException.class)
            .hasMessageContaining("Invalid email format");
    }
    
    @Test
    void customerValidation_shouldRejectInvalidPhone() {
        var customer = new Customer("jane@example.com", "Jane Smith", "123");
        
        assertThatThrownBy(() -> CustomerValidator.validateCustomer(customer))
            .isInstanceOf(ValidationException.class)
            .hasMessageContaining("Invalid phone format");
    }
    
    // Tests for DRY refactored version
    @Test
    void validationUtils_shouldValidateEmailCorrectly() {
        assertThat(ValidationUtils.isValidEmail("test@example.com")).isTrue();
        assertThat(ValidationUtils.isValidEmail("invalid-email")).isFalse();
        assertThat(ValidationUtils.isValidEmail("")).isFalse();
        assertThat(ValidationUtils.isValidEmail(null)).isFalse();
    }
    
    @Test
    void validationUtils_shouldValidateNameCorrectly() {
        assertThat(ValidationUtils.isValidName("John Doe")).isTrue();
        assertThat(ValidationUtils.isValidName("")).isFalse();
        assertThat(ValidationUtils.isValidName("   ")).isFalse();
        assertThat(ValidationUtils.isValidName(null)).isFalse();
    }
    
    @Test
    void validationUtils_shouldValidatePhoneCorrectly() {
        assertThat(ValidationUtils.isValidPhone("555-1234")).isTrue();
        assertThat(ValidationUtils.isValidPhone("(555) 123-4567")).isTrue();
        assertThat(ValidationUtils.isValidPhone("123")).isFalse();
        assertThat(ValidationUtils.isValidPhone("")).isFalse();
        assertThat(ValidationUtils.isValidPhone(null)).isFalse();
    }
}