package edu.trincoll.patterns.strategy;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Demonstration of the Strategy Pattern using modern Java features.
 * <p>
 * Shows multiple ways to use the Strategy pattern:
 * 1. Predefined strategies as constants
 * 2. Custom lambda strategies
 * 3. Method reference strategies
 * 4. Batch processing with strategy maps
 */
public class StrategyPatternDemo {

    public static void main(String[] args) {
        System.out.println("=== Strategy Pattern Demonstrations ===\n");

        demonstrateShippingStrategies();
        System.out.println("\n" + "=".repeat(60) + "\n");

        demonstratePayrollStrategies();
        System.out.println("\n" + "=".repeat(60) + "\n");

        demonstrateBatchProcessing();
    }

    private static void demonstrateShippingStrategies() {
        System.out.println("--- Shipping Strategy Pattern Demo ---");

        var calculator = new ShippingCalculator();

        // 1. Default strategy (STANDARD)
        System.out.println("\n1. Default Strategy:");
        var package1 = new ShippingData(5.0, 100.0, "Standard");
        System.out.println(calculator.getShippingSummary(package1));

        // 2. Switch to EXPRESS strategy
        System.out.println("2. EXPRESS Strategy:");
        calculator.setStrategy(ShippingStrategies.EXPRESS);
        System.out.println(calculator.getShippingSummary(package1));

        // 3. OVERNIGHT strategy
        System.out.println("3. OVERNIGHT Strategy:");
        calculator.setStrategy(ShippingStrategies.OVERNIGHT);
        var package2 = new ShippingData(3.0, 50.0, "Overnight");
        System.out.println(calculator.getShippingSummary(package2));

        // 4. Custom lambda strategy - weekend delivery
        System.out.println("4. Custom Lambda Strategy (Weekend Delivery):");
        calculator.setStrategy(data -> {
            double standardCost = ShippingStrategies.STANDARD.apply(data);
            return standardCost * 1.25; // 25% surcharge for weekend
        });
        var package3 = new ShippingData(2.0, 75.0, "Weekend");
        System.out.println(calculator.getShippingSummary(package3));

        // 5. BULK_DISCOUNT strategy
        System.out.println("5. BULK_DISCOUNT Strategy:");
        calculator.setStrategy(ShippingStrategies.BULK_DISCOUNT);
        var heavyPackage = new ShippingData(15.0, 200.0, "Bulk");
        System.out.println(calculator.getShippingSummary(heavyPackage));
    }

    private static void demonstratePayrollStrategies() {
        System.out.println("--- Payroll Strategy Pattern Demo ---");

        var processor = new PayrollProcessor();

        // Create test employees
        var hourlyEmployee = new Employee("Alice Johnson", 1001,
            LocalDate.of(2020, 3, 15));
        var salariedEmployee = new Employee("Bob Smith", 1002,
            LocalDate.of(2019, 7, 22));
        var salesEmployee = new Employee("Carol Sales", 1003,
            LocalDate.of(2021, 12, 1));

        // 1. Hourly with overtime
        System.out.println("\n1. HOURLY Strategy (45 hours at $25/hour):");
        processor.setCalculator(PayrollStrategies.HOURLY);
        var hourlyData = new PayrollData(hourlyEmployee, 45, 25.0);
        System.out.println(processor.getPayrollSummary(hourlyData));

        // 2. Salaried
        System.out.println("\n2. SALARIED Strategy ($80,000 annual):");
        processor.setCalculator(PayrollStrategies.SALARIED);
        var salariedData = new PayrollData(salariedEmployee, 80000.0);
        System.out.println(processor.getPayrollSummary(salariedData));

        // 3. Base plus commission
        System.out.println("\n3. BASE_PLUS_COMMISSION Strategy:");
        processor.setCalculator(PayrollStrategies.BASE_PLUS_COMMISSION);
        var salesData = new PayrollData(salesEmployee, 50000.0, 40000.0, 0.05);
        System.out.println(processor.getPayrollSummary(salesData));

        // 4. Custom tiered commission lambda
        System.out.println("\n4. Custom TIERED_COMMISSION Strategy:");
        processor.setCalculator(PayrollStrategies.TIERED_COMMISSION);
        var tieredData = new PayrollData(salesEmployee, 25000.0, 40000.0, 0.0);
        System.out.println(processor.getPayrollSummary(tieredData));
    }

    private static void demonstrateBatchProcessing() {
        System.out.println("--- Batch Processing with Strategy Map ---");

        // Create employees
        var alice = new Employee("Alice Johnson", 1001, LocalDate.of(2020, 3, 15));
        var bob = new Employee("Bob Smith", 1002, LocalDate.of(2019, 7, 22));
        var carol = new Employee("Carol Sales", 1003, LocalDate.of(2021, 12, 1));

        // Create payroll data batch
        List<PayrollData> payrollBatch = List.of(
            new PayrollData(alice, 40, 25.0),                    // Hourly
            new PayrollData(bob, 80000.0),                       // Salaried
            new PayrollData(carol, 30000.0, 45000.0, 0.05)      // Commission
        );

        // Strategy registry - map employees to their payment strategies
        Map<String, Function<PayrollData, Double>> employeeStrategies = Map.of(
            "Alice Johnson", PayrollStrategies.HOURLY,
            "Bob Smith", PayrollStrategies.SALARIED,
            "Carol Sales", PayrollStrategies.BASE_PLUS_COMMISSION
        );

        // Process all payments using parallel streams
        System.out.println("\nBatch processing results:");
        List<Double> payments = payrollBatch.parallelStream()
            .map(data -> employeeStrategies.get(data.employee().name()).apply(data))
            .toList();

        // Display results
        for (int i = 0; i < payrollBatch.size(); i++) {
            var data = payrollBatch.get(i);
            var payment = payments.get(i);
            System.out.printf("  %s: $%.2f%n", data.employee().name(), payment);
        }

        // Calculate total payroll
        double totalPayroll = payments.stream()
            .mapToDouble(Double::doubleValue)
            .sum();
        System.out.printf("\nTotal Payroll: $%.2f%n", totalPayroll);
    }
}
