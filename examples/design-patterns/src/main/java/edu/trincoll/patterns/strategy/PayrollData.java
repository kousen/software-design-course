package edu.trincoll.patterns.strategy;

/**
 * Data container for payroll information.
 * <p>
 * Uses multiple constructors for different employment types:
 * - Hourly workers: hoursWorked, hourlyRate
 * - Salaried workers: annualSalary
 * - Commission workers: salesAmount, baseSalary, commissionRate
 */
public record PayrollData(
    Employee employee,
    Integer hoursWorked,
    Double salesAmount,
    Double hourlyRate,
    Double annualSalary,
    Double baseSalary,
    Double commissionRate
) {
    // Constructor for hourly workers
    public PayrollData(Employee employee, int hoursWorked, double hourlyRate) {
        this(employee, hoursWorked, null, hourlyRate, null, null, null);
    }

    // Constructor for salaried workers
    public PayrollData(Employee employee, double annualSalary) {
        this(employee, null, null, null, annualSalary, null, null);
    }

    // Constructor for commission workers
    public PayrollData(Employee employee, double salesAmount,
                       double baseSalary, double commissionRate) {
        this(employee, null, salesAmount, null, null, baseSalary, commissionRate);
    }

    // Validation
    public PayrollData {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
    }
}
