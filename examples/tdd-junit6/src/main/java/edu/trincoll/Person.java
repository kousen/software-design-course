package edu.trincoll;

import java.time.LocalDate;
import java.time.Period;

/**
 * Person record for demonstrating AssertJ extracting assertions.
 */
public record Person(String first, String last, LocalDate dob) {

    public Person {
        if (first == null || first.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or blank");
        }
        if (last == null || last.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or blank");
        }
    }

    public Person(String first, String last) {
        this(first, last, null);
    }

    public String fullName() {
        return first + " " + last;
    }

    public int age() {
        if (dob == null) {
            throw new IllegalStateException("Date of birth not set");
        }
        return Period.between(dob, LocalDate.now()).getYears();
    }
}
