package edu.trincoll.patterns.strategy;

/**
 * Data container for shipping information.
 * Using Java record for immutable data carrier.
 */
public record ShippingData(
    String destination,
    double weight,
    double distance,
    String serviceType
) {
    // Convenience constructor for domestic shipping
    public ShippingData(double weight, double distance, String serviceType) {
        this("Domestic", weight, distance, serviceType);
    }

    // Validation in compact constructor
    public ShippingData {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        if (distance < 0) {
            throw new IllegalArgumentException("Distance cannot be negative");
        }
    }
}
