package edu.trincoll;

import java.time.LocalDate;

public record Person(String first, String last, LocalDate dob) {
    
    public String fullName() {
        return first + " " + last;
    }
    
    public int age() {
        return LocalDate.now().getYear() - dob.getYear();
    }
}