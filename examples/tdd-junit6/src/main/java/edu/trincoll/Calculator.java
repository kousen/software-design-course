package edu.trincoll;

import java.util.Arrays;

/**
 * Simple calculator for demonstrating TDD.
 * Each method was developed test-first using Red-Green-Refactor.
 */
public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int add(int... numbers) {
        return Arrays.stream(numbers).sum();
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }

    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    public boolean isPrime(int number) {
        if (number < 2) return false;
        if (number == 2) return true;
        if (number % 2 == 0) return false;
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) return false;
        }
        return true;
    }
}
