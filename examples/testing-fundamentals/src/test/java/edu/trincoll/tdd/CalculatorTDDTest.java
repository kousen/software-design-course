package edu.trincoll.tdd;

import edu.trincoll.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Calculator TDD Examples")
public class CalculatorTDDTest {
    
    private Calculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }
    
    @Test
    @DisplayName("Should add two positive numbers")
    void shouldAddTwoPositiveNumbers() {
        // Arrange
        int a = 2;
        int b = 3;
        
        // Act
        int result = calculator.add(a, b);
        
        // Assert
        assertThat(result).isEqualTo(5);
    }
    
    @Test
    @DisplayName("Should add positive and negative numbers")
    void shouldAddPositiveAndNegativeNumbers() {
        // Arrange
        int a = 10;
        int b = -3;
        
        // Act
        int result = calculator.add(a, b);
        
        // Assert
        assertThat(result).isEqualTo(7);
    }
    
    @Test
    @DisplayName("Should subtract two numbers")
    void shouldSubtractTwoNumbers() {
        // Arrange
        int a = 10;
        int b = 3;
        
        // Act
        int result = calculator.subtract(a, b);
        
        // Assert
        assertThat(result).isEqualTo(7);
    }
    
    @Test
    @DisplayName("Should multiply two numbers")
    void shouldMultiplyTwoNumbers() {
        // Arrange
        int a = 4;
        int b = 5;
        
        // Act
        int result = calculator.multiply(a, b);
        
        // Assert
        assertThat(result).isEqualTo(20);
    }
    
    @Test
    @DisplayName("Should divide two numbers")
    void shouldDivideTwoNumbers() {
        // Arrange
        int a = 10;
        int b = 2;
        
        // Act
        double result = calculator.divide(a, b);
        
        // Assert
        assertThat(result).isEqualTo(5.0);
    }
    
    @Test
    @DisplayName("Should throw exception when dividing by zero")
    void shouldThrowExceptionWhenDividingByZero() {
        // Arrange
        int a = 10;
        int b = 0;
        
        // Act & Assert
        assertThatThrownBy(() -> calculator.divide(a, b))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Division by zero is not allowed");
    }
}