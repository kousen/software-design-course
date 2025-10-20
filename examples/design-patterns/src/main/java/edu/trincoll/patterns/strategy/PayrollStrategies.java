package edu.trincoll.patterns.strategy;

import java.util.function.Function;

/**
 * Payroll calculation strategies using modern Function interface.
 * <p>
 * Demonstrates Strategy pattern for complex business calculations
 * with different payment methods:
 * - Hourly with overtime
 * - Salaried (bi-weekly)
 * - Commission only
 * - Base salary plus commission
 */
public class PayrollStrategies {

    // Hourly calculation with overtime (1.5x after 40 hours)
    public static final Function<PayrollData, Double> HOURLY = data -> {
        if (data.hoursWorked() == null || data.hourlyRate() == null) {
            throw new IllegalArgumentException("Hours worked and hourly rate required");
        }

        int hours = data.hoursWorked();
        double rate = data.hourlyRate();

        if (hours <= 0) return 0.0;
        if (hours <= 40) {
            return hours * rate;
        } else {
            return (40 * rate) + ((hours - 40) * rate * 1.5); // 1.5x overtime
        }
    };

    // Salaried calculation (bi-weekly = annual / 26)
    public static final Function<PayrollData, Double> SALARIED = data -> {
        if (data.annualSalary() == null) {
            throw new IllegalArgumentException("Annual salary required");
        }
        return data.annualSalary() / 26; // Bi-weekly
    };

    // Commission-only calculation
    public static final Function<PayrollData, Double> COMMISSION = data -> {
        if (data.salesAmount() == null || data.commissionRate() == null) {
            throw new IllegalArgumentException("Sales amount and commission rate required");
        }
        return data.salesAmount() * data.commissionRate();
    };

    // Base salary plus commission
    public static final Function<PayrollData, Double> BASE_PLUS_COMMISSION = data -> {
        if (data.baseSalary() == null || data.salesAmount() == null
            || data.commissionRate() == null) {
            throw new IllegalArgumentException(
                "Base salary, sales amount, and commission rate required"
            );
        }
        return data.baseSalary() + (data.salesAmount() * data.commissionRate());
    };

    // Tiered commission (5% up to $20k, 8% above)
    public static final Function<PayrollData, Double> TIERED_COMMISSION = data -> {
        if (data.baseSalary() == null || data.salesAmount() == null) {
            throw new IllegalArgumentException("Base salary and sales amount required");
        }

        double sales = data.salesAmount();
        double commission = sales <= 20000 ?
            sales * 0.05 :
            (20000 * 0.05) + ((sales - 20000) * 0.08);

        return data.baseSalary() + commission;
    };

    // Private constructor to prevent instantiation
    private PayrollStrategies() {
        throw new AssertionError("Utility class should not be instantiated");
    }
}
