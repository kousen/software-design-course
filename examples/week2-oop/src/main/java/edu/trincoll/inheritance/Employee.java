package edu.trincoll.inheritance;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {
    protected Long id;
    protected String name;
    protected String email;
    protected double salary;
    protected LocalDate hireDate;
    
    public Employee(String name, String email, double salary) {
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.hireDate = LocalDate.now();
    }
    
    public double calculateMonthlyPay() {
        return salary / 12;
    }
    
    public double calculateBonus() {
        return salary * 0.05;
    }
    
    public int getYearsOfService() {
        return LocalDate.now().getYear() - hireDate.getYear();
    }
    
    public void giveRaise(double percentage) {
        if (percentage < 0 || percentage > 50) {
            throw new IllegalArgumentException("Raise percentage must be between 0 and 50");
        }
        salary = salary * (1 + percentage / 100);
    }
    
    public String getEmployeeInfo() {
        return String.format("%s (%s) - $%.2f/year", name, getClass().getSimpleName(), salary);
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        this.salary = salary;
    }
    
    public LocalDate getHireDate() {
        return hireDate;
    }
    
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(email, employee.email);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
    
    @Override
    public String toString() {
        return getEmployeeInfo();
    }
}