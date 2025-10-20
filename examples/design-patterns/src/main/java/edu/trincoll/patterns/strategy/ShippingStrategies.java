package edu.trincoll.patterns.strategy;

import java.util.function.Function;

/**
 * Modern Strategy Pattern using Function interface and lambdas.
 * <p>
 * This demonstrates how modern Java simplifies the Strategy pattern:
 * - No need for custom strategy interface
 * - Use Function&lt;T, R&gt; from java.util.function
 * - Define strategies as lambda expressions
 * - Can be used as constants or created on-the-fly
 */
public class ShippingStrategies {

    // Standard shipping: $5 base + $0.50/lb + $0.10/mile
    public static final Function<ShippingData, Double> STANDARD = data ->
        5.0 + (data.weight() * 0.50) + (data.distance() * 0.10);

    // Express shipping: $12 base + $0.75/lb + $0.15/mile
    public static final Function<ShippingData, Double> EXPRESS = data ->
        12.0 + (data.weight() * 0.75) + (data.distance() * 0.15);

    // Overnight shipping: $25 base + $1.00/lb + $0.25/mile
    public static final Function<ShippingData, Double> OVERNIGHT = data ->
        25.0 + (data.weight() * 1.00) + (data.distance() * 0.25);

    // International shipping with customs fee
    public static final Function<ShippingData, Double> INTERNATIONAL = data -> {
        if (!"International".equals(data.destination())) {
            throw new IllegalArgumentException(
                "International strategy requires international destination"
            );
        }
        double baseRate = 30.0;
        double weightRate = 2.0;
        double distanceRate = 0.50;
        double customsFee = 15.0;

        return baseRate + (data.weight() * weightRate)
            + (data.distance() * distanceRate) + customsFee;
    };

    // Bulk discount strategy - 20% off for packages over 10 lbs
    public static final Function<ShippingData, Double> BULK_DISCOUNT = data -> {
        double standardCost = STANDARD.apply(data);
        return data.weight() > 10.0 ? standardCost * 0.8 : standardCost;
    };

    // Tiered weight pricing
    public static final Function<ShippingData, Double> TIERED = data -> {
        double base = 10.0;
        double weight = data.weight();

        if (weight <= 5) {
            return base + (weight * 0.50);
        } else if (weight <= 20) {
            return base + (5 * 0.50) + ((weight - 5) * 0.40); // discount tier
        } else {
            return base + (5 * 0.50) + (15 * 0.40) + ((weight - 20) * 0.30); // bulk tier
        }
    };

    // Private constructor to prevent instantiation
    private ShippingStrategies() {
        throw new AssertionError("Utility class should not be instantiated");
    }
}
