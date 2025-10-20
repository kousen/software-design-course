package edu.trincoll.patterns.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ShippingCalculator Tests")
class ShippingCalculatorTest {

    @Nested
    @DisplayName("Strategy Selection Tests")
    class StrategySelectionTests {

        @Test
        @DisplayName("Should use STANDARD strategy by default")
        void shouldUseStandardStrategyByDefault() {
            var calculator = new ShippingCalculator();
            var data = new ShippingData(5.0, 100.0, "Standard");

            double cost = calculator.calculateShipping(data);

            // STANDARD: 5.0 + (5.0 * 0.50) + (100.0 * 0.10) = 5 + 2.5 + 10 = 17.5
            assertThat(cost).isEqualTo(17.5);
        }

        @Test
        @DisplayName("Should allow strategy to be set in constructor")
        void shouldAllowStrategyInConstructor() {
            var calculator = new ShippingCalculator(ShippingStrategies.EXPRESS);
            var data = new ShippingData(5.0, 100.0, "Express");

            double cost = calculator.calculateShipping(data);

            // EXPRESS: 12.0 + (5.0 * 0.75) + (100.0 * 0.15) = 12 + 3.75 + 15 = 30.75
            assertThat(cost).isEqualTo(30.75);
        }

        @Test
        @DisplayName("Should allow strategy to be changed at runtime")
        void shouldAllowStrategyChangeAtRuntime() {
            var calculator = new ShippingCalculator();
            var data = new ShippingData(5.0, 100.0, "Test");

            // Start with default (STANDARD)
            double standardCost = calculator.calculateShipping(data);
            assertThat(standardCost).isEqualTo(17.5);

            // Change to EXPRESS
            calculator.setStrategy(ShippingStrategies.EXPRESS);
            double expressCost = calculator.calculateShipping(data);
            assertThat(expressCost).isEqualTo(30.75);

            // Change to OVERNIGHT
            calculator.setStrategy(ShippingStrategies.OVERNIGHT);
            double overnightCost = calculator.calculateShipping(data);
            assertThat(overnightCost).isEqualTo(55.0); // 25 + (5 * 1.00) + (100 * 0.25)
        }

        @Test
        @DisplayName("Should reject null strategy")
        void shouldRejectNullStrategy() {
            var calculator = new ShippingCalculator();

            assertThatThrownBy(() -> calculator.setStrategy(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Strategy cannot be null");
        }

        @Test
        @DisplayName("Should accept custom lambda strategy")
        void shouldAcceptCustomLambdaStrategy() {
            var calculator = new ShippingCalculator();
            var data = new ShippingData(5.0, 100.0, "Custom");

            // Custom strategy: flat rate based on weight
            Function<ShippingData, Double> flatRate = d -> d.weight() * 2.0;
            calculator.setStrategy(flatRate);

            double cost = calculator.calculateShipping(data);
            assertThat(cost).isEqualTo(10.0); // 5.0 * 2.0
        }
    }

    @Nested
    @DisplayName("Predefined Strategy Tests")
    class PredefinedStrategyTests {

        @Test
        @DisplayName("STANDARD strategy should calculate correctly")
        void standardStrategyShouldCalculateCorrectly() {
            var calculator = new ShippingCalculator(ShippingStrategies.STANDARD);

            var data1 = new ShippingData(10.0, 50.0, "Standard");
            assertThat(calculator.calculateShipping(data1)).isEqualTo(15.0);
            // 5.0 + (10.0 * 0.50) + (50.0 * 0.10) = 5 + 5 + 5 = 15

            var data2 = new ShippingData(2.0, 200.0, "Standard");
            assertThat(calculator.calculateShipping(data2)).isEqualTo(26.0);
            // 5.0 + (2.0 * 0.50) + (200.0 * 0.10) = 5 + 1 + 20 = 26
        }

        @Test
        @DisplayName("EXPRESS strategy should calculate correctly")
        void expressStrategyShouldCalculateCorrectly() {
            var calculator = new ShippingCalculator(ShippingStrategies.EXPRESS);

            var data1 = new ShippingData(10.0, 50.0, "Express");
            assertThat(calculator.calculateShipping(data1)).isEqualTo(27.0);
            // 12.0 + (10.0 * 0.75) + (50.0 * 0.15) = 12 + 7.5 + 7.5 = 27

            var data2 = new ShippingData(2.0, 200.0, "Express");
            assertThat(calculator.calculateShipping(data2)).isEqualTo(43.5);
            // 12.0 + (2.0 * 0.75) + (200.0 * 0.15) = 12 + 1.5 + 30 = 43.5
        }

        @Test
        @DisplayName("OVERNIGHT strategy should calculate correctly")
        void overnightStrategyShouldCalculateCorrectly() {
            var calculator = new ShippingCalculator(ShippingStrategies.OVERNIGHT);

            var data1 = new ShippingData(10.0, 50.0, "Overnight");
            assertThat(calculator.calculateShipping(data1)).isEqualTo(47.5);
            // 25.0 + (10.0 * 1.00) + (50.0 * 0.25) = 25 + 10 + 12.5 = 47.5

            var data2 = new ShippingData(2.0, 200.0, "Overnight");
            assertThat(calculator.calculateShipping(data2)).isEqualTo(77.0);
            // 25.0 + (2.0 * 1.00) + (200.0 * 0.25) = 25 + 2 + 50 = 77
        }

        @Test
        @DisplayName("BULK_DISCOUNT strategy should apply discount for packages over 10 lbs")
        void bulkDiscountStrategyShouldApplyDiscount() {
            var calculator = new ShippingCalculator(ShippingStrategies.BULK_DISCOUNT);

            // Under 10 lbs - no discount
            var lightPackage = new ShippingData(5.0, 100.0, "Bulk");
            assertThat(calculator.calculateShipping(lightPackage)).isEqualTo(17.5);

            // Exactly 10 lbs - no discount
            var mediumPackage = new ShippingData(10.0, 100.0, "Bulk");
            assertThat(calculator.calculateShipping(mediumPackage)).isEqualTo(20.0);

            // Over 10 lbs - 20% discount
            var heavyPackage = new ShippingData(15.0, 100.0, "Bulk");
            double expected = (5.0 + (15.0 * 0.50) + (100.0 * 0.10)) * 0.8;
            assertThat(calculator.calculateShipping(heavyPackage)).isEqualTo(expected);
        }

        @Test
        @DisplayName("INTERNATIONAL strategy should add customs fee")
        void internationalStrategyShouldAddCustomsFee() {
            var calculator = new ShippingCalculator(ShippingStrategies.INTERNATIONAL);
            var data = new ShippingData("International", 5.0, 100.0, "International");

            double cost = calculator.calculateShipping(data);

            // 30.0 + (5.0 * 2.0) + (100.0 * 0.50) + 15.0 = 30 + 10 + 50 + 15 = 105
            assertThat(cost).isEqualTo(105.0);
        }

        @Test
        @DisplayName("INTERNATIONAL strategy should reject domestic destination")
        void internationalStrategyShouldRejectDomesticDestination() {
            var calculator = new ShippingCalculator(ShippingStrategies.INTERNATIONAL);
            var data = new ShippingData("Domestic", 5.0, 100.0, "International");

            assertThatThrownBy(() -> calculator.calculateShipping(data))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("International strategy requires international destination");
        }

        @Test
        @DisplayName("TIERED strategy should apply weight-based pricing")
        void tieredStrategyShouldApplyWeightBasedPricing() {
            var calculator = new ShippingCalculator(ShippingStrategies.TIERED);

            // Under 5 lbs: base + weight * 0.50
            var light = new ShippingData(3.0, 0, "Tiered");
            assertThat(calculator.calculateShipping(light)).isEqualTo(11.5);
            // 10 + (3 * 0.50) = 11.5

            // 5-20 lbs: base + (5 * 0.50) + (weight-5) * 0.40
            var medium = new ShippingData(10.0, 0, "Tiered");
            assertThat(calculator.calculateShipping(medium)).isEqualTo(14.5);
            // 10 + (5 * 0.50) + (5 * 0.40) = 10 + 2.5 + 2 = 14.5

            // Over 20 lbs: base + (5 * 0.50) + (15 * 0.40) + (weight-20) * 0.30
            var heavy = new ShippingData(25.0, 0, "Tiered");
            assertThat(calculator.calculateShipping(heavy)).isEqualTo(20.0);
            // 10 + 2.5 + 6 + 1.5 = 20
        }
    }

    @Nested
    @DisplayName("Summary Generation Tests")
    class SummaryGenerationTests {

        @Test
        @DisplayName("Should generate formatted shipping summary")
        void shouldGenerateFormattedShippingSummary() {
            var calculator = new ShippingCalculator(ShippingStrategies.STANDARD);
            var data = new ShippingData("Domestic", 5.0, 100.0, "Standard");

            String summary = calculator.getShippingSummary(data);

            assertThat(summary)
                .contains("Weight: 5.0 lbs")
                .contains("Distance: 100.0 miles")
                .contains("Destination: Domestic")
                .contains("Service: Standard")
                .contains("Shipping Cost: $17.50");
        }
    }

    @Nested
    @DisplayName("Validation Tests")
    class ValidationTests {

        @Test
        @DisplayName("Should reject null shipping data")
        void shouldRejectNullShippingData() {
            var calculator = new ShippingCalculator();

            assertThatThrownBy(() -> calculator.calculateShipping(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Shipping data cannot be null");
        }
    }
}
