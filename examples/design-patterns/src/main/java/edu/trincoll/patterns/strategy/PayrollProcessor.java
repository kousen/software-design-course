package edu.trincoll.patterns.strategy;

import java.util.function.Function;

/**
 * Payroll processor using Strategy pattern.
 * <p>
 * Context class that:
 * - Maintains reference to a payroll calculation strategy
 * - Allows strategy changes at runtime
 * - Delegates calculations to current strategy
 * - Provides formatted summaries
 */
public class PayrollProcessor {
    private Function<PayrollData, Double> calculator;

    // Constructor with default strategy
    public PayrollProcessor() {
        this.calculator = PayrollStrategies.SALARIED;
    }

    // Constructor with specific strategy
    public PayrollProcessor(Function<PayrollData, Double> calculator) {
        this.calculator = calculator;
    }

    // Change strategy at runtime
    public void setCalculator(Function<PayrollData, Double> calculator) {
        if (calculator == null) {
            throw new IllegalArgumentException("Calculator cannot be null");
        }
        this.calculator = calculator;
    }

    // Process payroll using current strategy
    public double processPayroll(PayrollData data) {
        if (data == null || data.employee() == null) {
            throw new IllegalArgumentException("Payroll data and employee cannot be null");
        }
        return calculator.apply(data);
    }

    // Get formatted payroll summary
    public String getPayrollSummary(PayrollData data) {
        double pay = processPayroll(data);
        var summary = new StringBuilder();
        summary.append("Employee: %s%n".formatted(data.employee().name()));

        appendIfPresent(summary, "Hours Worked: %d%n", data.hoursWorked());
        appendIfPresent(summary, "Hourly Rate: $%.2f%n", data.hourlyRate());
        appendIfPresent(summary, "Annual Salary: $%.2f%n", data.annualSalary());
        appendIfPresent(summary, "Sales Amount: $%.2f%n", data.salesAmount());
        appendIfPresent(summary, "Base Salary: $%.2f%n", data.baseSalary());

        if (data.commissionRate() != null) {
            summary.append("Commission Rate: %.1f%%%n"
                .formatted(data.commissionRate() * 100));
        }

        summary.append("Pay Amount: $%.2f%n".formatted(pay));

        // Add strategy type information
        if (calculator == PayrollStrategies.HOURLY) {
            summary.append("Strategy: Hourly with Overtime");
        } else if (calculator == PayrollStrategies.SALARIED) {
            summary.append("Strategy: Salaried");
        } else if (calculator == PayrollStrategies.COMMISSION) {
            summary.append("Strategy: Commission Only");
        } else if (calculator == PayrollStrategies.BASE_PLUS_COMMISSION) {
            summary.append("Strategy: Base Plus Commission");
        } else if (calculator == PayrollStrategies.TIERED_COMMISSION) {
            summary.append("Strategy: Tiered Commission");
        } else {
            summary.append("Strategy: Custom");
        }

        return summary.toString();
    }

    // Helper method to reduce repetitive null checks
    private <T> void appendIfPresent(StringBuilder sb, String format, T value) {
        if (value != null) {
            sb.append(format.formatted(value));
        }
    }
}
