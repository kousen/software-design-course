package edu.trincoll.patterns.strategy;

import java.time.LocalDate;

/**
 * Simple employee record for strategy pattern demonstrations.
 */
public record Employee(
    String name,
    int id,
    LocalDate hireDate
) {
    public Employee {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        if (hireDate == null) {
            throw new IllegalArgumentException("Hire date cannot be null");
        }
    }
}
