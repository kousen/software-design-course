package edu.trincoll.patterns.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.*;

@DisplayName("PayrollProcessor Tests")
class PayrollProcessorTest {

    private Employee hourlyEmployee;
    private Employee salariedEmployee;
    private Employee salesEmployee;

    @BeforeEach
    void setUp() {
        hourlyEmployee = new Employee("Alice", 1001, LocalDate.of(2020, 1, 1));
        salariedEmployee = new Employee("Bob", 1002, LocalDate.of(2019, 1, 1));
        salesEmployee = new Employee("Carol", 1003, LocalDate.of(2021, 1, 1));
    }

    @Nested
    @DisplayName("HOURLY Strategy Tests")
    class HourlyStrategyTests {

        @Test
        @DisplayName("Should calculate regular hours correctly")
        void shouldCalculateRegularHours() {
            var processor = new PayrollProcessor(PayrollStrategies.HOURLY);
            var data = new PayrollData(hourlyEmployee, 40, 25.0);

            double pay = processor.processPayroll(data);

            assertThat(pay).isEqualTo(1000.0); // 40 * 25
        }

        @Test
        @DisplayName("Should calculate overtime at 1.5x rate")
        void shouldCalculateOvertimeCorrectly() {
            var processor = new PayrollProcessor(PayrollStrategies.HOURLY);
            var data = new PayrollData(hourlyEmployee, 45, 25.0);

            double pay = processor.processPayroll(data);

            // (40 * 25) + (5 * 25 * 1.5) = 1000 + 187.50 = 1187.50
            assertThat(pay).isEqualTo(1187.50);
        }

        @Test
        @DisplayName("Should handle zero hours")
        void shouldHandleZeroHours() {
            var processor = new PayrollProcessor(PayrollStrategies.HOURLY);
            var data = new PayrollData(hourlyEmployee, 0, 25.0);

            double pay = processor.processPayroll(data);

            assertThat(pay).isEqualTo(0.0);
        }

        @Test
        @DisplayName("Should calculate large overtime correctly")
        void shouldCalculateLargeOvertime() {
            var processor = new PayrollProcessor(PayrollStrategies.HOURLY);
            var data = new PayrollData(hourlyEmployee, 60, 20.0);

            double pay = processor.processPayroll(data);

            // (40 * 20) + (20 * 20 * 1.5) = 800 + 600 = 1400
            assertThat(pay).isEqualTo(1400.0);
        }

        @Test
        @DisplayName("Should reject missing hours")
        void shouldRejectMissingHours() {
            var processor = new PayrollProcessor(PayrollStrategies.HOURLY);
            var data = new PayrollData(hourlyEmployee, null, null, 25.0, null, null, null);

            assertThatThrownBy(() -> processor.processPayroll(data))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Hours worked and hourly rate required");
        }

        @Test
        @DisplayName("Should reject missing rate")
        void shouldRejectMissingRate() {
            var processor = new PayrollProcessor(PayrollStrategies.HOURLY);
            var data = new PayrollData(hourlyEmployee, 40, null, null, null, null, null);

            assertThatThrownBy(() -> processor.processPayroll(data))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Hours worked and hourly rate required");
        }
    }

    @Nested
    @DisplayName("SALARIED Strategy Tests")
    class SalariedStrategyTests {

        @Test
        @DisplayName("Should calculate bi-weekly salary correctly")
        void shouldCalculateBiWeeklySalary() {
            var processor = new PayrollProcessor(PayrollStrategies.SALARIED);
            var data = new PayrollData(salariedEmployee, 52000.0);

            double pay = processor.processPayroll(data);

            assertThat(pay).isEqualTo(2000.0); // 52000 / 26
        }

        @Test
        @DisplayName("Should handle various salary amounts")
        void shouldHandleVariousSalaryAmounts() {
            var processor = new PayrollProcessor(PayrollStrategies.SALARIED);

            var data1 = new PayrollData(salariedEmployee, 80000.0);
            assertThat(processor.processPayroll(data1))
                .isCloseTo(3076.92, within(0.01));

            var data2 = new PayrollData(salariedEmployee, 100000.0);
            assertThat(processor.processPayroll(data2))
                .isCloseTo(3846.15, within(0.01));
        }

        @Test
        @DisplayName("Should use SALARIED as default strategy")
        void shouldUseSalariedAsDefaultStrategy() {
            var processor = new PayrollProcessor(); // No strategy specified
            var data = new PayrollData(salariedEmployee, 52000.0);

            double pay = processor.processPayroll(data);

            assertThat(pay).isEqualTo(2000.0);
        }

        @Test
        @DisplayName("Should reject missing salary")
        void shouldRejectMissingSalary() {
            var processor = new PayrollProcessor(PayrollStrategies.SALARIED);
            var data = new PayrollData(salariedEmployee, null, null, null, null, null, null);

            assertThatThrownBy(() -> processor.processPayroll(data))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Annual salary required");
        }
    }

    @Nested
    @DisplayName("COMMISSION Strategy Tests")
    class CommissionStrategyTests {

        @Test
        @DisplayName("Should calculate commission only correctly")
        void shouldCalculateCommissionOnly() {
            var processor = new PayrollProcessor(PayrollStrategies.COMMISSION);
            var data = new PayrollData(salesEmployee, null, 50000.0, null, null, null, 0.05);

            double pay = processor.processPayroll(data);

            assertThat(pay).isEqualTo(2500.0); // 50000 * 0.05
        }

        @Test
        @DisplayName("Should handle different commission rates")
        void shouldHandleDifferentCommissionRates() {
            var processor = new PayrollProcessor(PayrollStrategies.COMMISSION);

            var data1 = new PayrollData(salesEmployee, null, 100000.0, null, null, null, 0.03);
            assertThat(processor.processPayroll(data1)).isEqualTo(3000.0);

            var data2 = new PayrollData(salesEmployee, null, 100000.0, null, null, null, 0.10);
            assertThat(processor.processPayroll(data2)).isEqualTo(10000.0);
        }

        @Test
        @DisplayName("Should reject missing sales amount")
        void shouldRejectMissingSalesAmount() {
            var processor = new PayrollProcessor(PayrollStrategies.COMMISSION);
            var data = new PayrollData(salesEmployee, null, null, null, null, null, 0.05);

            assertThatThrownBy(() -> processor.processPayroll(data))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Sales amount and commission rate required");
        }
    }

    @Nested
    @DisplayName("BASE_PLUS_COMMISSION Strategy Tests")
    class BasePlusCommissionStrategyTests {

        @Test
        @DisplayName("Should calculate base plus commission correctly")
        void shouldCalculateBasePlusCommission() {
            var processor = new PayrollProcessor(PayrollStrategies.BASE_PLUS_COMMISSION);
            var data = new PayrollData(salesEmployee, 50000.0, 40000.0, 0.05);

            double pay = processor.processPayroll(data);

            assertThat(pay).isEqualTo(42500.0); // 40000 + (50000 * 0.05)
        }

        @Test
        @DisplayName("Should handle zero sales")
        void shouldHandleZeroSales() {
            var processor = new PayrollProcessor(PayrollStrategies.BASE_PLUS_COMMISSION);
            var data = new PayrollData(salesEmployee, 0.0, 40000.0, 0.05);

            double pay = processor.processPayroll(data);

            assertThat(pay).isEqualTo(40000.0); // Base only
        }

        @Test
        @DisplayName("Should handle high sales volumes")
        void shouldHandleHighSalesVolumes() {
            var processor = new PayrollProcessor(PayrollStrategies.BASE_PLUS_COMMISSION);
            var data = new PayrollData(salesEmployee, 500000.0, 50000.0, 0.02);

            double pay = processor.processPayroll(data);

            assertThat(pay).isEqualTo(60000.0); // 50000 + (500000 * 0.02)
        }

        @Test
        @DisplayName("Should reject missing fields")
        void shouldRejectMissingFields() {
            var processor = new PayrollProcessor(PayrollStrategies.BASE_PLUS_COMMISSION);
            var data = new PayrollData(salesEmployee, null, null, null, null, 40000.0, 0.05);

            assertThatThrownBy(() -> processor.processPayroll(data))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Base salary, sales amount, and commission rate required");
        }
    }

    @Nested
    @DisplayName("TIERED_COMMISSION Strategy Tests")
    class TieredCommissionStrategyTests {

        @Test
        @DisplayName("Should apply 5% rate for sales under $20k")
        void shouldApplyFivePercentUnderTwentyK() {
            var processor = new PayrollProcessor(PayrollStrategies.TIERED_COMMISSION);
            var data = new PayrollData(salesEmployee, 15000.0, 40000.0, 0.0);

            double pay = processor.processPayroll(data);

            assertThat(pay).isEqualTo(40750.0); // 40000 + (15000 * 0.05)
        }

        @Test
        @DisplayName("Should apply tiered rates for sales over $20k")
        void shouldApplyTieredRatesOverTwentyK() {
            var processor = new PayrollProcessor(PayrollStrategies.TIERED_COMMISSION);
            var data = new PayrollData(salesEmployee, 25000.0, 40000.0, 0.0);

            double pay = processor.processPayroll(data);

            // 40000 + (20000 * 0.05) + (5000 * 0.08) = 40000 + 1000 + 400 = 41400
            assertThat(pay).isEqualTo(41400.0);
        }

        @Test
        @DisplayName("Should apply correct rates at $20k boundary")
        void shouldApplyCorrectRatesAtBoundary() {
            var processor = new PayrollProcessor(PayrollStrategies.TIERED_COMMISSION);
            var data = new PayrollData(salesEmployee, 20000.0, 40000.0, 0.0);

            double pay = processor.processPayroll(data);

            assertThat(pay).isEqualTo(41000.0); // 40000 + (20000 * 0.05)
        }
    }

    @Nested
    @DisplayName("Custom Strategy Tests")
    class CustomStrategyTests {

        @Test
        @DisplayName("Should accept custom lambda strategy")
        void shouldAcceptCustomLambdaStrategy() {
            var processor = new PayrollProcessor();

            // Custom: flat rate per hour, no overtime
            Function<PayrollData, Double> flatRate = data ->
                data.hoursWorked() * data.hourlyRate();

            processor.setCalculator(flatRate);
            var data = new PayrollData(hourlyEmployee, 45, 20.0);

            double pay = processor.processPayroll(data);

            assertThat(pay).isEqualTo(900.0); // No overtime premium
        }

        @Test
        @DisplayName("Should allow strategy switching at runtime")
        void shouldAllowStrategySwitchingAtRuntime() {
            var processor = new PayrollProcessor();

            // Start with SALARIED (default)
            var salariedData = new PayrollData(salariedEmployee, 52000.0);
            assertThat(processor.processPayroll(salariedData)).isEqualTo(2000.0);

            // Switch to HOURLY
            processor.setCalculator(PayrollStrategies.HOURLY);
            var hourlyData = new PayrollData(hourlyEmployee, 40, 25.0);
            assertThat(processor.processPayroll(hourlyData)).isEqualTo(1000.0);

            // Switch to COMMISSION
            processor.setCalculator(PayrollStrategies.COMMISSION);
            var commissionData = new PayrollData(salesEmployee, null, 50000.0, null, null, null, 0.05);
            assertThat(processor.processPayroll(commissionData)).isEqualTo(2500.0);
        }
    }

    @Nested
    @DisplayName("Summary Generation Tests")
    class SummaryGenerationTests {

        @Test
        @DisplayName("Should generate summary for hourly employee")
        void shouldGenerateSummaryForHourlyEmployee() {
            var processor = new PayrollProcessor(PayrollStrategies.HOURLY);
            var data = new PayrollData(hourlyEmployee, 45, 25.0);

            String summary = processor.getPayrollSummary(data);

            assertThat(summary)
                .contains("Employee: Alice")
                .contains("Hours Worked: 45")
                .contains("Hourly Rate: $25.00")
                .contains("Pay Amount: $1187.50")
                .contains("Strategy: Hourly with Overtime");
        }

        @Test
        @DisplayName("Should generate summary for salaried employee")
        void shouldGenerateSummaryForSalariedEmployee() {
            var processor = new PayrollProcessor(PayrollStrategies.SALARIED);
            var data = new PayrollData(salariedEmployee, 80000.0);

            String summary = processor.getPayrollSummary(data);

            assertThat(summary)
                .contains("Employee: Bob")
                .contains("Annual Salary: $80000.00")
                .contains("Strategy: Salaried");
        }

        @Test
        @DisplayName("Should generate summary for commission employee")
        void shouldGenerateSummaryForCommissionEmployee() {
            var processor = new PayrollProcessor(PayrollStrategies.BASE_PLUS_COMMISSION);
            var data = new PayrollData(salesEmployee, 50000.0, 40000.0, 0.05);

            String summary = processor.getPayrollSummary(data);

            assertThat(summary)
                .contains("Employee: Carol")
                .contains("Sales Amount: $50000.00")
                .contains("Base Salary: $40000.00")
                .contains("Commission Rate: 5.0%")
                .contains("Strategy: Base Plus Commission");
        }
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should reject null payroll data")
        void shouldRejectNullPayrollData() {
            var processor = new PayrollProcessor();

            assertThatThrownBy(() -> processor.processPayroll(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Payroll data and employee cannot be null");
        }

        @Test
        @DisplayName("Should reject null employee")
        void shouldRejectNullEmployee() {
            var processor = new PayrollProcessor();

            assertThatThrownBy(() ->
                new PayrollData(null, null, null, null, 52000.0, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Employee cannot be null");
        }

        @Test
        @DisplayName("Should reject null calculator")
        void shouldRejectNullCalculator() {
            var processor = new PayrollProcessor();

            assertThatThrownBy(() -> processor.setCalculator(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Calculator cannot be null");
        }
    }
}
