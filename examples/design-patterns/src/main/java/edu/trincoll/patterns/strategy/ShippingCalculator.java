package edu.trincoll.patterns.strategy;

import java.util.function.Function;

/**
 * Context class for the Strategy pattern.
 * <p>
 * The context:
 * - Maintains a reference to a strategy (Function interface)
 * - Allows strategy to be changed at runtime
 * - Delegates calculation to the current strategy
 * <p>
 * Modern implementation uses Function&lt;T, R&gt; instead of custom interface.
 */
public class ShippingCalculator {
    private Function<ShippingData, Double> strategy;

    // Constructor with default strategy
    public ShippingCalculator() {
        this.strategy = ShippingStrategies.STANDARD;
    }

    // Constructor with specific strategy
    public ShippingCalculator(Function<ShippingData, Double> strategy) {
        this.strategy = strategy;
    }

    // Change strategy at runtime
    public void setStrategy(Function<ShippingData, Double> strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }
        this.strategy = strategy;
    }

    // Calculate shipping using current strategy
    public double calculateShipping(ShippingData data) {
        if (data == null) {
            throw new IllegalArgumentException("Shipping data cannot be null");
        }
        return strategy.apply(data);
    }

    // Get formatted summary
    public String getShippingSummary(ShippingData data) {
        double cost = calculateShipping(data);
        return """
               Package Details:
                 Weight: %.1f lbs
                 Distance: %.1f miles
                 Destination: %s
                 Service: %s
               Shipping Cost: $%.2f
               """.formatted(
            data.weight(),
            data.distance(),
            data.destination(),
            data.serviceType(),
            cost
        );
    }
}
